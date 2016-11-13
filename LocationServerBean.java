package uqu.hspa.mobile;

import oracle.adfmf.amx.event.ActionEvent;

import oracle.adf.model.datacontrols.device.DeviceManager;
import oracle.adf.model.datacontrols.device.DeviceManagerFactory;
import oracle.adf.model.datacontrols.device.DeviceManagerFactory;
import oracle.adf.model.datacontrols.device.GeolocationCallback;

import java.sql.ResultSet;

import java.sql.SQLException;


import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import java.util.concurrent.TimeUnit;

import javax.el.MethodExpression;
import javax.el.ValueExpression;

import oracle.adf.model.datacontrols.device.Location;



import oracle.adfmf.framework.api.AdfmfJavaUtilities;

import oracle.adfmf.framework.model.AdfELContext;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

import oracle.adfmf.json.JSONException;
import oracle.adfmf.json.JSONObject;

import oracle.adfmf.util.BundleFactory;


import oracle.maf.api.cdm.persistence.manager.DBPersistenceManager;
import oracle.maf.api.cdm.persistence.manager.RestJSONPersistenceManager;

import oracle.maf.api.cdm.persistence.model.Entity;
import oracle.maf.api.cdm.persistence.util.EntityUtils;

//import uqu.hspa.application.model.Person;


///import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;

//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;

public class LocationServerBean {

   // private static Object mContext;
    private DeviceManager deviceManager;
       //private MapCore bean;
   
       
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public static String LOG = "Log";

    // JSONParser jsonParser = new JSONParser();

  //  private final Context mContext=null;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;
    
    boolean started= false;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES =0;   // 0 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 100*60*2 ; // 2mn

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
     uqu.hspa.application.model.Location loc;
    //Long temp;

  String  watchId;
    // The minimum distance to change Updates in meters
 //   private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 50; // 0 meters

    // The minimum time between updates in milliseconds
 //   private static final long MIN_TIME_BW_UPDATES = 1000*60*1 ; // 1 second

    // Declaring a Location Manager
   //protected LocationManager locationManager;
 //  protected LocationBean locationbean= new LocationBean(this.mContext);

//GeolocationCallback bean;
  //  private Object lon;


    public void postLocation(ActionEvent actionEvent) {
        // Add event code here...
    }
    
    
    
    public LocationServerBean() {
      // this.mContext = context;
    }
/*
    public Location getLocation () {
             try {
                 locationManager = (LocationManager) mContext
                         .getSystemService(mContext.LOCATION_SERVICE);

                 // getting GPS status
                 isGPSEnabled = locationManager
                         .isProviderEnabled(LocationManager.GPS_PROVIDER);

                 // getting network status
                 isNetworkEnabled = locationManager
                         .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                 if (!isGPSEnabled && !isNetworkEnabled) {
                     // no network provider is enabled
                 } else {
                     this.canGetLocation = true;
                     if (isNetworkEnabled) {
                      //updates will be send according to these arguments
                         locationManager.requestLocationUpdates(
                                 LocationManager.NETWORK_PROVIDER,
                                 MIN_TIME_BW_UPDATES,
                                 MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                         Log.d("Network", "Network Enaibled");
                         if (locationManager != null) {
                             location = locationManager
                                     .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                             if (location != null) {
                                 latitude = location.getLatitude();
                                 longitude = location.getLongitude();
                             }
                         }
                     }
                     // if GPS Enabled get lat/long using GPS Services
                     if (isGPSEnabled) {
                         if (location == null) {
                       //    bean.setLocationWatcherID(
      deviceManager.startUpdatingPosition(3000, true, "MapBean.mainGeoListener", bean);
                           //);
            location= deviceManager.getCurrentPosition(10000, 10000, true)   ; 
                             locationManager.requestLocationUpdates(
                                     LocationManager.GPS_PROVIDER,
                                     MIN_TIME_BW_UPDATES,
                                     MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                             
                             Log.d("GPS Enabled", "GPS Enabled");
                             if (locationManager != null) {
                                 location = locationManager
                                         .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                 if (location != null) {
                                     latitude = location.getLatitude();
                                     longitude = location.getLongitude();
                                 }
                             }
                         }
                     }
                 }

             } catch (Exception e) {
                 e.printStackTrace();
             }
             return location;
         }
*/
    private Integer getIdPerson() {
     //   ValueExpression veLatitude =
      //      AdfmfJavaUtilities.getValueExpression("#{bindings.person.collectionModel.idPerson.inputValue}", Integer.class);
     //   return ((Integer)veLatitude.getValue(AdfmfJavaUtilities.getAdfELContext()));
     int idPerson=0;
     DBPersistenceManager pm = new DBPersistenceManager();
     String sql = "SELECT ID_PERSON FROM PERSON";
     ResultSet set = pm.executeSqlSelect(sql, null);
     try {
       set.first();
        idPerson = set.getInt(1);
     } 
     
         
     catch (SQLException e) {
       //sLog.severe("Error executing SQL statement: "+e.getLocalizedMessage());
     }
     return idPerson;
     }
   /*
   Boolean localLocationTableIsEmpty() {
       boolean isEmpty=false;
       RestJSONPersistenceManager restj=new RestJSONPersistenceManager();
       String rowElementName="rowElementName";
       
     //  String JSONText=restj.getSerializedDataObject(loc, null, rowElementName, true);
      DBPersistenceManager pm = new DBPersistenceManager();
       String sql = "SELECT  PHI,GI,LOCATION_TIME,PERSON_ID  FROM Location";
       ResultSet set = pm.executeSqlSelect(sql, null);
        try {
           while(set.next())
           {
               double longi=set.getDouble(1);
           double lati=set.getDouble(2);
           String t1=set.getString(3);
               
         //  PHI NUMERIC ,
          //      GI NUMERIC ,
           //     LOCATION_TIME VARCHAR ,
             //   PERSON_ID NUMERIC ,
           String JonSonStr=getJonsonString(longi, lati,t1) ; 
           String result = restj.invokeRestService(connectionName,requestType , requestUri, JonSonStr,  getheaderParameter(), retryLimit, secured);
           
        // set.first();
        //  isEmpty = set.getBoolean(1);
       } 
           pm.deleteAllRows(Location.class);
       }
       catch (SQLException e) {
         //sLog.severe("Error executing SQL statement: "+e.getLocalizedMessage());
       }
     Map<String,String> searchAttrs = new HashMap<String,String>();
     searchAttrs.put("LOCATION_ID","true");
   //  searchAttrs.put("lastName","King");
    // List<uqu.hspa.application.model.Location> locs = pm.find(Location.class,searchAttrs);
      
    
  //int size=     locs.size();
  //if( size!=0) isEmpty=true;
     
       return isEmpty;
   }
   */
   
    
  //  #{bindings.idPerson.inputValue}
  //  #{row.idPerson}

    void startGPS(){
        
        try
        {
                 
   String     watch = DeviceManagerFactory.getDeviceManager().startUpdatingPosition(2000, false, "HajiGPSSubscriptionID", new GeolocationCallback()
              {
                public void locationUpdated(Location position) {
                    System.out.println("Location updated to: " + position);
                }
            });
        this.setWatchId(watch);
            setStarted(true);
        }
        catch (oracle.adfmf.framework.exception.AdfException e)
        {
        //                System.out.println("Connection Listener Exception:" + e);
        //     try {
           //     System.out.println("Sleeping thread to retry");
           //     Thread.sleep(500);
         //   } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
           //     e1.printStackTrace();
           // }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    Location currentPosition=null;
    Location oldLocation;  
    
    

    /**
     * Getter for longitude
     * @return the value for the longitude retrieved from the startLocationMonitor method
     */
  /*  public void locationUpdated(oracle.adf.model.datacontrols.device.Location currentLocation) {
        this.setLatitude(currentLocation.getLatitude());
        this.setLongitude(currentLocation.getLongitude());
        this.setWatchId(currentLocation.getWatchId());
        this.setLocation(currentLocation);
    }
    
    private double getLatitude() {
        ValueExpression veLatitude =
            AdfmfJavaUtilities.getValueExpression("#{pageFlowScope.LocationBeanServer.latitude}", Double.class);
        return ((Double)veLatitude.getValue(AdfmfJavaUtilities.getAdfELContext())).doubleValue();
    }
    private double getLongitude() {

        ValueExpression veLongitude =
            AdfmfJavaUtilities.getValueExpression("#{pageFlowScope.LocationBeanServer.longitude}", Double.class);
        return ((Double)veLongitude.getValue(AdfmfJavaUtilities.getAdfELContext())).doubleValue();

    }
  public void  invokeELGPS() {
   //     #{pageFlowScope.LocationBean.started or !deviceScope.hardware.hasGeolocation}
        try {
            if (!isStarted()) {

                AdfELContext adfELContext = AdfmfJavaUtilities.getAdfELContext();

                MethodExpression me =
                    AdfmfJavaUtilities.getMethodExpression("#{bindings.startLocationMonitor.execute}", Object.class,
                                                           new Class[] { });

                me.invoke(adfELContext, new Object[] { });
                this.setStarted(true);
            }
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
       
      
    }
    
    public void stopLocationMonitor(ActionEvent actionEvent) {
        if (getWatchId().length() > 0) {
            DeviceManagerFactory.getDeviceManager().clearWatchPosition(getWatchId());
            setWatchId("");
           
        }
    }*/
    public void stopGPSMonitor() {
        if (getWatchId().length() > 0) {
            try {
                DeviceManagerFactory.getDeviceManager().clearWatchPosition(getWatchId());
                setWatchId("");
            } catch (Exception e) {
                // TODO: Add catch code
                e.printStackTrace();
            }
        this.setStarted(false);
    }
    }
    public void setStarted(boolean started) {
        boolean oldStarted = this.started;
        this.started = started;
        propertyChangeSupport.firePropertyChange("started", oldStarted, started);
    }

    public boolean isStarted() {
        return started;
    }

    //Property change listeners

    /**
     * Method that adds a new propertyChangeListener to a specific component.
     * @param listener is the listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Method that removes a new propertyChangeListener to a specific component.
     * @param listener is the listener to add
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    String connectionName="HSPARest";
        String requestType="POST"; 
    
    int retryLimit=0;
    boolean secured=false;
    String requestUri =null;//= "/Persons/"+getIdPerson()+"/child/LocationsView";  
    public Map<String,String>     getheaderParameter(){
            Map<String,String> headerParameter = new HashMap<String,String>();
            headerParameter.put("Content-Type", "application/vnd.oracle.adf.resourceitem+json");
            return headerParameter;
        }

    void setrequestUri(int ipPerson){
         requestUri = "/Persons/"+ipPerson+"/child/LocationsView";  
    }
    public void sendLocation(ActionEvent actionEvent) {
        this.deviceManager = DeviceManagerFactory.getDeviceManager();
        setrequestUri(getIdPerson());
        connectionName="HSPARest";
     requestType="POST"; 
        
        int retryLimit=0;
        boolean secured=false;
          
        //invokeELGPS();
       // getLatitude();
      //  getLongitude() ;
      /*   startGPS();
        try
        {
       if (isStarted())         currentPosition = DeviceManagerFactory.getDeviceManager().getCurrentPosition(1000, true);
            
        
        }           catch (oracle.adfmf.framework.exception.AdfException e)
                                {
                    //                System.out.println("Connection Listener Exception:" + e);
                                }
        oldLocation=currentPosition;*/
      ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        
        try {
           executor.execute(
new Runnable() {
                private Location location;

                @Override
                public void run() {
                    // TODO Implement this method
               
           
            
     //   BackgroundTaskExecutor.getInstance().execute(
      //   () -> { 
           // mimic slow SOAP or REST call by waiting for 3 seconds
       //  int i=0;
         while (true)
         
         { 
             
            try {
             //sleep une munite
                Thread.sleep(MIN_TIME_BW_UPDATES);
                   
            } catch (InterruptedException e) {
            } 
             RestJSONPersistenceManager rpm = new RestJSONPersistenceManager();    
                  
          //  Double longx= new Double(getLongitude());
           //Double latx= new Double(getLatitude());
             
         Boolean isOnline= DeviceManagerFactory.getDeviceManager().isDeviceOnline();
        //   String getNetworkStatus= DeviceManagerFactory.getDeviceManager().getNetworkStatus();
            //   UWP
              oldLocation=this.location;  
           
                  // Checks whether device has network connectivity regardless of type.
              //    String watchID=null;
             if (!isStarted())   startGPS();
                                try
                                {
                                         
                        
                 //          System.out.println("Connection thread trying to hook to listener");
                              //currentPosition=new Location();                                    
                      if (isStarted())           currentPosition = DeviceManagerFactory.getDeviceManager().getCurrentPosition(1000, true);
                               setLocation(currentPosition);
                                //location=deviceManager.getCurrentPosition(500, true);
                               
                               // double longit= currentPosition.getLongitude();
                              //   double lati= currentPosition.getLatitude();
                                
                             //   if (oldLocation==null) {
                             //        oldLocation.setLongitude(new Double(0.0)); 
                             //        oldLocation.setLatitude(new Double(0.0));
                             //    }
                                 
                              
                           
                                }
                                catch (oracle.adfmf.framework.exception.AdfException e)
                                {
                    //                System.out.println("Connection Listener Exception:" + e);
                               //     try {
                                   //     System.out.println("Sleeping thread to retry");
                                   //     Thread.sleep(500);
                                 //   } catch (InterruptedException e1) {
                                        // TODO Auto-generated catch block
                                   //     e1.printStackTrace();
                                   // }
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
             // juste pour tester enlever le block  suivant pour l'execution normale
            // currentPosition = DeviceManagerFactory.getDeviceManager().getCurrentPosition(1000, true);
           
               
                        //    }
                       
             //  double longit=0.0;
              // double lati=0.0;
             //  double oldLongit=0.0;
             //  double oldLati=0.0;
             try{   
              //  double longit= currentPosition.getLongitude();
              //  double  lati= currentPosition.getLatitude();
             
                // double oldLongit= oldLocation.getLongitude();
                // double  oldLati= oldLocation.getLatitude();
                 
            //     if(onChangeLocation(oldLocation,currentPosition))
                // if(onChangeLocation(oldLongit,oldLati,longit,lati))
                 {
                    
                           
                     String  jsonInString = getJonsonString(location.getLongitude(), location.getLatitude(), getJSONtime());
                    
                   if(isOnline)      
                    {
                           //     localLocationTableIsEmpty() ;// AdfmfJavaUtilities.flushDataChangeEvent();
                  String result = rpm.invokeRestService(connectionName,requestType , requestUri, jsonInString,  getheaderParameter(), retryLimit, secured);
                 
                    //.handleReadResponse(result, loc, null, null, null, false);
                          if(result!=null)
                                     {
                       //            rpm.handleReadResponse(result, Location.class, null, null, null, false);
                       writeTolocalDB();
                                       }
                                     else  {  writeTolocalDB(); }
                                 }
                  else 
                   {                     
                        writeTolocalDB();                        
                        
                    }
                 
                 }
             }
               catch(Exception e)
               {
                   e.printStackTrace();
               }
               
              // this.setLongitude(longit);
              // this.setLatitude(lati);
             
          /*   if (oldLocation==null){
                 oldLongit=0.0;
                 oldLati= 0.0;
                 
             }
             
               if (currentPosition==null){
                   longit=0.0;
                   oldLati= 0.0;
               }*/
         //       if(onChangeLocation(oldLocation,currentPosition))
                
         try
         {
            // if(this.getWatchId()!=null)
              //   DeviceManagerFactory.getDeviceManager().clearWatchPosition(  this.getWatchId());
                   if (isStarted())           stopGPSMonitor();
         }
         catch (oracle.adfmf.framework.exception.AdfException e)
         {
         System.out.println("Connection Listener Exception:" + e);
         } 
           }   
        
     //    });
     
     }
     });
     }
     catch (RejectedExecutionException e) {
     // the queue is full, and you're using the AbortPolicy as the
     // RejectedExecutionHandler
     }
        System.out.println("Connection failed Exception");       
    }
    /*
    BackgroundTaskExecutor.getInstance().execute( () ->
    { // mimic slow SOAP or REST call by waiting for 3 seconds 
      try { Thread.sleep(3000); }
      catch (InterruptedException e) { } 
      departments.add(new Department("1","Marketing")); 
      departments.add(new Department("2","Sales")); 
      departments.add(new Department("3","Support"));
      providerChangeSupport.fireProviderRefresh("departments"); 
      AdfmfJavaUtilities.flushDataChangeEvent(); 
      }); */ 
    
   public void writeTolocalDB() {
       DBPersistenceManager pm = new DBPersistenceManager();
       loc=new uqu.hspa.application.model.Location();
       loc.setGi(this.getLongitude());
       loc.setPhi(this.getLatitude());
       // loc.setGi(1.1);
       //    loc.setPhi(1.1);
       loc.setLocationTime(getJSONtime());
       loc.setPersonId(getIdPerson());
       loc.setIsNewEntity(true);
       
       pm.insertEntity(loc, true);
   }
    
    String getJonsonString(double longi, double lati, String temp)  {
       JSONObject jsonObject = new JSONObject();
    

        try {
            jsonObject.put("Gi", longi);
            jsonObject.put("Phi", lati) ;
                jsonObject.put("LocationTime", temp);
        } catch (JSONException e) {
        }
      
  
        return jsonObject.toString();
    }
  //  private   Boolean onChangeLocation(double lat1,double lon1,  double lat2, double lon2) {
   private   Boolean onChangeLocation(Location l1,Location l2) {
      if (l1==null || l2==null) return false;
       
        
                double lat1=l1.getAltitude();
                 double lon1=l1.getLongitude();
                double lat2=l2.getAltitude();
                 double lon2=l2.getLongitude();
      
     //   if (  lat1==0 ||  lon1==0 ||    lat2==0 ||   lon==0 ) return false;
                   Boolean isChanged=false;
                    double theta = lon1 - lon2;
                    double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
                    dist = Math.acos(dist);
                    dist = rad2deg(dist);
                    dist = dist * 60 * 1.1515 * 1609.344;
                if (dist >= MIN_DISTANCE_CHANGE_FOR_UPDATES) isChanged=true;
                  //  if (unit == "K") {
                  //          dist = dist * 1.609344;
                   // } else if (unit == "N") {
                   //         dist = dist * 0.8684;
                  //  }
      
                    return (isChanged);
            }

            /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
            /*::    This function converts decimal degrees to radians                                                :*/
            /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
            private static double deg2rad(double deg) {
                    return (deg * Math.PI / 180.0);
            }

            /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
            /*::    This function converts radians to decimal degrees                                                :*/
            /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
            private static double rad2deg(double rad) {
                    return (rad * 180 / Math.PI);
            }
   
    
    
    public String getJSONtime(){
     
         Date dt=new Date();
      
        int hour=dt.getHours();
       int minutes=dt.getMinutes();
        int sec=dt.getSeconds();
        int year=dt.getYear()+1900;
        int month=dt.getMonth();
        int days=dt.getDay();
        
        
        
     return   String.format("%04d-%02d-%02d%s%02d:%02d:%02d%s", year,month,days,"T",hour,minutes,sec,"+03");
        
    }
    
  
    public void onLocationChanged(Location location) {
        // TODO Implement this method
    }

  
    public void setLatitude(double latitude) {
        double oldLatitude = this.latitude;
        this.latitude = latitude;
        propertyChangeSupport.firePropertyChange("latitude", oldLatitude, latitude);
    }

   
    public void setLongitude(double longitude) {
        double oldLongitude = this.longitude;
        this.longitude = longitude;
        propertyChangeSupport.firePropertyChange("longitude", oldLongitude, longitude);
    }

   
   

    public void setWatchId(String watchId) {
        this.watchId = watchId;
    }

    public String getWatchId() {
        return watchId;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
