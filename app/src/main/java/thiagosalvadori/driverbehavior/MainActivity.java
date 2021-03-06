package thiagosalvadori.driverbehavior;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    // Templates
    private double[] LEFT_TURN_TEMPLATE = {-0.076614, -0.057461, -0.062249, -0.074460, -0.076483, -0.066913, -0.088467, -0.122351, -0.120064, -0.102568, -0.107017, -0.116989, -0.137954, -0.182771, -0.160225, -0.129230, -0.143837, -0.128984, -0.116789, -0.143510, -0.115266, -0.101841, -0.112072, -0.108384, -0.135526, -0.124919, -0.143572, -0.142140, -0.159933, -0.150021, -0.142520, -0.152632, -0.137339, -0.090249, -0.085737, -0.079535, -0.079389, -0.071588, -0.064178, -0.076292, -0.059070, -0.023555, 0.008268, 0.025093, 0.044907, 0.025424, 0.045221, 0.031468, 0.029895, 0.034146, 0.020947, -0.033731, -0.055029, -0.086754, -0.118808, -0.147344, -0.178284, -0.205762, -0.224204, -0.241724, -0.271776, -0.309902, -0.344206, -0.401695, -0.479294, -0.587489, -0.678782, -0.742527, -0.787761, -0.840310, -0.894063, -0.979604, -1.068530, -1.154925, -1.214016, -1.300798, -1.373664, -1.462040, -1.555575, -1.646348, -1.734498, -1.831647, -1.900955, -1.995528, -2.083457, -2.216789, -2.299401, -2.372137, -2.429743, -2.484469, -2.551782, -2.629136, -2.706454, -2.781821, -2.820859, -2.873267, -2.953701, -2.993721, -3.035571, -3.048514, -3.074217, -3.102465, -3.146540, -3.190326, -3.237668, -3.290305, -3.342226, -3.381974, -3.402496, -3.448807, -3.452580, -3.479148, -3.490981, -3.509883, -3.529756, -3.548635, -3.522517, -3.553250, -3.574785, -3.568428, -3.608358, -3.630969, -3.621803, -3.601603, -3.599651, -3.605459, -3.553515, -3.504169, -3.445797, -3.417159, -3.372715, -3.349647, -3.295171, -3.212773, -3.113426, -3.028623, -2.921245, -2.836474, -2.732958, -2.600141, -2.496949, -2.416155, -2.316416, -2.242733, -2.163158, -2.039677, -1.943439, -1.865421, -1.747250, -1.654141, -1.600165, -1.545056, -1.508026, -1.472847, -1.393459, -1.316124, -1.277133, -1.261161, -1.155965, -1.151797, -1.101868, -1.039113, -1.008227, -0.984630, -0.973706, -0.911613, -0.885186, -0.827519, -0.765074, -0.788112, -0.790845, -0.778117, -0.777519, -0.736727, -0.730537, -0.743809, -0.731519, -0.735165, -0.734799, -0.738282, -0.733929, -0.737455, -0.740805, -0.749733, -0.758215, -0.735627, -0.719915, -0.741380, -0.736872, -0.730674, -0.692225, -0.688260, -0.699815, -0.678232, -0.640490, -0.608465, -0.574211, -0.518686, -0.504244, -0.479031, -0.455080};
    private double[] RIGHT_TURN_TEMPLATE = {-0.268151, -0.268151, -0.281558, -0.313449, -0.299692, -0.259808, -0.223833, -0.195403, -0.156902, -0.124158, -0.106457, -0.064743, -0.048098, -0.062931, -0.036801, -0.044537, -0.048057, -0.045654, -0.054863, -0.050205, -0.041948, 0.002287, 0.027072, 0.048703, 0.059675, 0.027961, 0.016986, -0.001101, -0.035523, -0.031831, -0.053224, -0.035240, -0.043055, -0.010256, -0.034643, -0.050149, -0.001673, 0.017564, 0.003279, 0.026099, -0.021175, -0.020116, -0.009533, 0.012012, 0.020988, 0.083146, 0.107719, 0.109995, 0.102580, 0.082128, 0.078021, 0.035813, 0.043599, 0.060573, 0.044137, 0.018945, 0.029490, 0.064408, 0.080341, 0.083985, 0.089363, 0.130863, 0.151135, 0.181886, 0.182368, 0.184742, 0.175505, 0.229937, 0.285477, 0.322918, 0.345080, 0.381456, 0.433251, 0.455642, 0.532459, 0.603519, 0.686350, 0.740139, 0.816138, 0.903660, 0.994468, 1.076905, 1.176288, 1.261126, 1.349383, 1.473450, 1.551091, 1.617189, 1.710627, 1.787902, 1.863228, 1.948195, 1.998268, 2.051583, 2.136710, 2.188849, 2.247958, 2.294536, 2.375176, 2.405815, 2.459822, 2.516875, 2.584483, 2.618064, 2.676782, 2.767040, 2.812563, 2.876878, 2.905417, 2.940190, 2.975140, 2.993019, 2.996597, 3.003827, 3.004949, 3.067307, 3.151446, 3.143272, 3.156576, 3.188368, 3.208993, 3.236249, 3.267888, 3.261553, 3.259366, 3.245796, 3.240566, 3.233682, 3.194581, 3.144027, 3.134309, 3.100176, 3.050513, 2.986094, 2.882758, 2.792250, 2.702437, 2.668829, 2.594764, 2.553132, 2.484852, 2.398916, 2.273224, 2.180632, 2.031378, 1.947047, 1.872679, 1.721585, 1.608690, 1.478456, 1.358565, 1.273398, 1.196321, 1.109690, 1.067613, 0.998909, 0.937472, 0.886767, 0.830937, 0.733845, 0.668422, 0.585201, 0.552111, 0.478536, 0.450779, 0.445478, 0.427035, 0.350138, 0.374769, 0.290908, 0.301262, 0.198093, 0.207342, 0.151006, 0.124302, 0.028065, 0.011339, -0.000720, 0.016554, 0.006149, 0.015419, 0.018479, -0.009260, -0.081581, -0.146455, -0.123809, -0.244033, -0.220339, -0.216984, -0.177404, -0.120650, -0.141432, -0.172668, -0.219580, -0.252654, -0.217037, -0.225339, -0.237056, -0.177320, -0.174200, -0.165490};
    private double[] LEFT_LANE_CHANGE = {0.459687, 0.430956, 0.405578, 0.387214, 0.385092, 0.350514, 0.354058, 0.317201, 0.224727, 0.152199, 0.113943, 0.071854, 0.026123, 0.026732, 0.023481, 0.005068, -0.039238, -0.087076, -0.103791, -0.133078, -0.157070, -0.160709, -0.164165, -0.154042, -0.146340, -0.190737, -0.202270, -0.255363, -0.282817, -0.324222, -0.371218, -0.381387, -0.429356, -0.448110, -0.529134, -0.575461, -0.609895, -0.656015, -0.665352, -0.739345, -0.744516, -0.804973, -0.816439, -0.842655, -0.880967, -0.903957, -0.973681, -1.028426, -1.049788, -1.100729, -1.137629, -1.155447, -1.224089, -1.245245, -1.282581, -1.318051, -1.317271, -1.351006, -1.344747, -1.296663, -1.245238, -1.192553, -1.157825, -1.151648, -1.072997, -0.986786, -0.910632, -0.805724, -0.721384, -0.608701, -0.532297, -0.415660, -0.324009, -0.223532, -0.122334, -0.014703, 0.102869, 0.204986, 0.305828, 0.357574, 0.422056, 0.473737, 0.519003, 0.571583, 0.610041, 0.637000, 0.683680, 0.672480, 0.682909, 0.690902, 0.690833, 0.708006, 0.712829, 0.744225, 0.770221, 0.785340, 0.830349, 0.806069, 0.790666, 0.799016, 0.787796, 0.775221, 0.742206, 0.666789, 0.671756, 0.668814, 0.698581, 0.667482, 0.735622, 0.733318, 0.713890, 0.703095, 0.706248, 0.695835, 0.601667, 0.592653, 0.564935, 0.561588, 0.520101, 0.517081, 0.431850, 0.404512, 0.439832, 0.469555, 0.442246, 0.466103, 0.463867, 0.500050, 0.417586, 0.413945, 0.387502, 0.410265, 0.395498, 0.354654, 0.335006, 0.304848, 0.281944, 0.237201, 0.208103, 0.168967, 0.081989, 0.079805, 0.031761, 0.078057, 0.097139, 0.136335, 0.121857, 0.108103, 0.144835, 0.135678, 0.111656, 0.073512, 0.058344, 0.097565, 0.090772, 0.046010, 0.022641, -0.001476, 0.023498, 0.058715, 0.044287, 0.036327, 0.023018, -0.006863, 0.026041, 0.017078, 0.012393, -0.022703, -0.048383, -0.074694, -0.069044, -0.052185, -0.049575, -0.027943, -0.034207, -0.022920, -0.000705, 0.043383, 0.033553, 0.039537, 0.026068, 0.022849, -0.024262, 0.022920, 0.035181, 0.071729, 0.079635, 0.089061, 0.092269, 0.129794, 0.092658, 0.088025, 0.112354, 0.070345, 0.072574, 0.055537, 0.085322, 0.107871, 0.115885, 0.090937, 0.086390};
    private double[] RIGHT_LANE_CHANGE = {0.153229, 0.199198, 0.191153, 0.164357, 0.156139, 0.113856, 0.113909, 0.115875, 0.110081, 0.114154, 0.119939, 0.131180, 0.145690, 0.157559, 0.166919, 0.214119, 0.232143, 0.258843, 0.272716, 0.289726, 0.305886, 0.317406, 0.299621, 0.317201, 0.337733, 0.362984, 0.386973, 0.382947, 0.400192, 0.410828, 0.426678, 0.428329, 0.462458, 0.464235, 0.490822, 0.481604, 0.522646, 0.548229, 0.536140, 0.551471, 0.546882, 0.557845, 0.564429, 0.586007, 0.631406, 0.663042, 0.723743, 0.727778, 0.800565, 0.839067, 0.883305, 0.902346, 0.910859, 0.964915, 0.979876, 1.076450, 1.124141, 1.146464, 1.198317, 1.226507, 1.245627, 1.302098, 1.321269, 1.364381, 1.378523, 1.409195, 1.403858, 1.406449, 1.372518, 1.376676, 1.371049, 1.375280, 1.388877, 1.399878, 1.393091, 1.411543, 1.390766, 1.371027, 1.352275, 1.326799, 1.283443, 1.188625, 1.110041, 1.046877, 1.007941, 1.032243, 0.957646, 0.915510, 0.885057, 0.850381, 0.817439, 0.803382, 0.795774, 0.727255, 0.729200, 0.669755, 0.620945, 0.542013, 0.417229, 0.348484, 0.221884, 0.139921, 0.063972, -0.042656, -0.132460, -0.192875, -0.215792, -0.279702, -0.344247, -0.432379, -0.489290, -0.570170, -0.624022, -0.652197, -0.663641, -0.670681, -0.734831, -0.755550, -0.805879, -0.857523, -0.845292, -0.795366, -0.769005, -0.749709, -0.737123, -0.734743, -0.707583, -0.695188, -0.694905, -0.660160, -0.615660, -0.554231, -0.538012, -0.541757, -0.459124, -0.420845, -0.376818, -0.344570, -0.327341, -0.333958, -0.357483, -0.356847, -0.340920, -0.320043, -0.273395, -0.232911, -0.179127, -0.183578, -0.189722, -0.185982, -0.161360, -0.189684, -0.180200, -0.188428, -0.198160, -0.148030, -0.173189, -0.187514, -0.241345, -0.236940, -0.282554, -0.312479, -0.337078, -0.329801, -0.296072, -0.277438, -0.303789, -0.323076, -0.322245, -0.378916, -0.396362, -0.411021, -0.409623, -0.419788, -0.412206, -0.362865, -0.338976, -0.323943, -0.324984, -0.360449, -0.371157, -0.392822, -0.365519, -0.375974, -0.334191, -0.340466, -0.306204, -0.287063, -0.240149, -0.264533, -0.264714, -0.276378, -0.262559, -0.255177, -0.271149, -0.249930, -0.277656, -0.275265, -0.261502, -0.261835, -0.248743};

    // Thresholds
    private float ACC_THRESHOLD = 3;
    private float SAFETY_THRESHOLD = 4;
    private int SPEED_THRESHOLD = 120;
    private float EVENT_THRESHOLD = 2;

    // Accelerometer
    private static final int SENSOR_DELAY_TIME = 40000; // 25Hz
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private double accleratorscale=1.124;//加速度补偿因子传感器送来的加速度垂直方向最大值为8.8

    // GPS
    private LocationManager locationManager;
    private LocationListener locationListener;

    // Sensor Data
    private int windowSize = 150; // 6 seconds * 25 samples per second
    private ArrayList<Float> lateralAxisData = new ArrayList<>();
    private ArrayList<Float> longAxisData = new ArrayList<>();
    private boolean xPossibleEvent = false;

    private boolean notSafe = false;
    private boolean firstHalf = false;

    // Smooth
    private float[] AccData;
    final float ALPHA = 0.05f;

    // Record
    private boolean record = false;
    private int recordType;
    File latFile;
    File longFile;

    // Chart Layout
    private LinearLayout chartLayout;
    private Chart chart;

    private Util util = new Util();
    private MediaPlayer alert;

    private float windowOverlappingPerc = 0.25f;
    private int windowNonOverlappingSize = (int) (windowSize*(1-windowOverlappingPerc));
    private boolean lastWindowDetected = false;
    private int jnicallbackalarm=0;


    private int axis;
    SharedPreferences.OnSharedPreferenceChangeListener listener;
    SharedPreferences prefs;
    static {
        System.loadLibrary("native-lib");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stringFromJNI();
        startharshdetection();
        setInsuranceTheshold();//
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        axis = Integer.valueOf(prefs.getString("orientation", "1"));
        recordType = Integer.valueOf(prefs.getString("record", "1"));

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if (key.equals("orientation")){
                    axis = Integer.valueOf(prefs.getString("orientation", ""));
                }
            }
        };

        prefs.registerOnSharedPreferenceChangeListener(listener);

        // create accelerometer sensor listener
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SENSOR_DELAY_TIME);

        // create GPS sensor listener
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        locationListener = new gpsListener();
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        } catch (SecurityException e){}

        // create line chart
        chartLayout = (LinearLayout) findViewById(R.id.chart);

        // add to main layout
        chart = new Chart(this);

        RelativeLayout.LayoutParams chartParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        chart.getChart().setLayoutParams(chartParams);
        chartLayout.addView(chart.getChart());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.record:
                if (record){
                    record = false;
                    item.setTitle("Record");
                    util.showAlert(this, "Record ended");
                } else{
                    record = true;
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    // Make sure the Download directory exists.
                    path.mkdirs();
                    String timeStamp = new SimpleDateFormat("dd_MM_yyyy-H_mm").format(Calendar.getInstance().getTime());
                    latFile = new File(path, "Lateral_Data_" + timeStamp + ".txt");
                    longFile = new File(path, "Long_Data_" + timeStamp + ".txt");
                    item.setTitle("Stop recording");
                    util.showAlert(this, "Record started");
                }
                return true;
            case R.id.history:
                Intent intent = new Intent (this, HistoryActivity.class);
                startActivity(intent);
                return true;
           case R.id.action_settings:
                Intent intent2 = new Intent (this, SettingsActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;
        double accrx;
        double accry;
        double accrz;

        // Smooth the sensor data
        AccData = lowPassFilter(event.values.clone(), AccData);
        accrx=AccData[0]*accleratorscale;
        accry=AccData[1]*accleratorscale;
        accrz=AccData[2]*accleratorscale;

        //SensorEvent.values[0]：加速度在X轴的负值
        //SensorEvent.values[1]：加速度在Y轴的负值
        //SensorEvent.values[2]：加速度在Z轴的负值

        readaccrdata( accrx,accry, accrz  );

        lateralAxisData.add(AccData[0]);
        longAxisData.add(AccData[axis]);

        // check for a possible event
        if (AccData[0] > EVENT_THRESHOLD || AccData[0] < -EVENT_THRESHOLD){
            xPossibleEvent = true;
        }

        // check for safety threshold
        if (AccData[0] > SAFETY_THRESHOLD || AccData[0] < -SAFETY_THRESHOLD){
            notSafe = true;
        }
        if (AccData[axis] > SAFETY_THRESHOLD || AccData[axis] < -SAFETY_THRESHOLD){
            notSafe = true;
        }

        if (lateralAxisData.size() == windowSize) {
            // Save data
            if (record) {
                util.record(this, lateralAxisData, longAxisData, latFile, longFile);
                if (recordType == 2){
                    record = false;
                    util.showAlert(this, "Record ended");
                }
                /*try{
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } catch (SecurityException e){}
                */
            }

            // check for aggressive acc or break
            checkAcceleration(longAxisData);

            int i = 0;
            while (i < windowNonOverlappingSize && !firstHalf){
                if (lateralAxisData.get(i) > EVENT_THRESHOLD || lateralAxisData.get(i) < -EVENT_THRESHOLD) {
                    firstHalf = true;
                }
                i++;
            }

            if (xPossibleEvent && firstHalf && !lastWindowDetected){
                DTW rightTurnComp = new DTW(lateralAxisData, RIGHT_TURN_TEMPLATE);
                DTW leftTurnComp = new DTW(lateralAxisData, LEFT_TURN_TEMPLATE);
                DTW leftLaneChangeComp = new DTW(lateralAxisData, LEFT_LANE_CHANGE);
                DTW rightLaneChangeComp = new DTW(lateralAxisData, RIGHT_LANE_CHANGE);

                double WP[] = new double[4];
                WP[0] = rightTurnComp.getDistance();
                WP[1] = leftTurnComp.getDistance();
                WP[2] = rightLaneChangeComp.getDistance();
                WP[3] = leftLaneChangeComp.getDistance();

                double shortest = WP[0];
                int index = 0;
                for (i = 1; i<4; i++) {
                    if (WP[i] < shortest){
                        shortest = WP[i];
                        index = i;
                    }
                }

                switch(index){
                    case 0:
                        if (notSafe){
                            //util.showAlert(this, "Warning: Risky Right Turn");
                            //alert  = MediaPlayer.create(this, R.raw.warning_aggressive_right_turn);
                           // alert.start();
                            util.saveManeuver(this, lateralAxisData, "Risky_Right_Turn");
                        } else{
                            //util.showAlert(this, "Alert: Right Turn");
                           // alert  = MediaPlayer.create(this, R.raw.right_turn);
                            //alert.start();
                            util.saveManeuver(this, lateralAxisData, "Right_Turn");
                        }
                        break;
                    case 1:
                        if (notSafe){
                           // util.showAlert(this, "Warning: Risky Left Turn");
                           // alert  = MediaPlayer.create(this, R.raw.warning_aggressive_left_turn);
                           // alert.start();
                            util.saveManeuver(this, lateralAxisData, "Risky_Left_Turn");
                        } else{
                            //util.showAlert(this, "Alert: Left Turn");
                           // alert  = MediaPlayer.create(this, R.raw.left_turn);
                           // alert.start();
                            util.saveManeuver(this, lateralAxisData, "Left_Turn");
                        }
                        break;
                    case 2:
                        if (notSafe){
                            //util.showAlert(this, "Warning: Risky Right Lane Change");
                          //  alert  = MediaPlayer.create(this, R.raw.warning_aggressive_right_lane_change);
                          //  alert.start();
                            util.saveManeuver(this, lateralAxisData, "Risky_Right_Lane_Change");
                        } else{
                            //util.showAlert(this, "Alert: Right Lane Change");
                          //  alert  = MediaPlayer.create(this, R.raw.right_lane_change);
                           // alert.start();
                            util.saveManeuver(this, lateralAxisData, "Right_Lane_Change");
                        }
                        break;
                    case 3:
                        if (notSafe){
                            //util.showAlert(this, "Warning: Risky Left Lane Change");
                           // alert  = MediaPlayer.create(this, R.raw.warning_aggressive_left_lane_change);
                           // alert.start();
                            util.saveManeuver(this, lateralAxisData, "Risky_Left_Lane_Change");
                        } else{
                            //util.showAlert(this, "Alert: Left Lane Change");
                            //alert  = MediaPlayer.create(this, R.raw.left_lane_change);
                            //alert.start();
                            util.saveManeuver(this, lateralAxisData, "Left_Lane_Change");
                        }
                        break;
                    }
                lastWindowDetected = true;
            } else{
                lastWindowDetected = false;
            }
            xPossibleEvent = false;
            firstHalf = false;
            notSafe = false;

             //stringFromJNI();

            // slide the window
            for (i = 0; i < windowNonOverlappingSize; i++){
                lateralAxisData.remove(0);
                longAxisData.remove(0);
            }
        }

        // add entry to the graph
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (chart.getSelectedAxis().equals("xAxis")) {
                chart.addEntry(AccData[0]);
            } else if (chart.getSelectedAxis().equals("yAxis")) {
                chart.addEntry(AccData[1]);
            } else if (chart.getSelectedAxis().equals("zAxis")) {
                chart.addEntry(AccData[2]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

public void Setsensitivy(int sensitivity)
{
    switch (sensitivity)
    {
        case 0:
            setLowsensitivity();
            break;
        case 1:
            setMediumsensitivity();
            break;
        case 2:
            setHighsensitivity();
            break;
       default:
             setInsuranceTheshold();
             break;

    }
}

/*
Low sensitivity: Beep less (too forgiving!)
Acceleration: 5.292 to 20
Braking: -20 to -5.6448
Left corner: 5.6448 to 20
Right corner: -20 to -5.6448
 */
public void setLowsensitivity()
{
    setthreshold(2,5.292);// 急刹车阈值g
    setthreshold(3,5.6448);// 急加速阈值g
    setthreshold(4,5.6448);// 急转弯阈值g
    setthreshold(5,2.1);// 轻微碰撞阈值g
    setthreshold(8,9);//急加速速度阈值speed
    setthreshold(9,20.3);//急减速速度阈值speed
}
/*
Medium sensitivity: Beep normal (recommended settings)
Acceleration: 4.41 to 20
Braking: -20 to -4.7628
Left corner: 4.7628 to 20
Right corner: -20 to -4.7628

 */
    public void setMediumsensitivity()
    {
        setthreshold(2,4.41);// 急刹车阈值G
        setthreshold(3,4.7628);// 急加速阈值g
        setthreshold(4,4.7628);// 急转弯阈值g
        setthreshold(5,2.1);// 轻微碰撞阈值g
        setthreshold(8,9);//急加速速度阈值g
        setthreshold(9,15.8);//急减速速度阈值g
    }
    /*
    High sensitivity: Beep more (very sensitive use sparingly)
Acceleration: 3.528 to 20
Braking: -20 to -3.8808
Left corner: 3.8808 to 20
Right corner: -20 to -3.8808

     */
    public void setHighsensitivity()
    {
        setthreshold(2,3.528);// 急刹车阈值g
        setthreshold(3,3.8808);// 急加速阈值g
        setthreshold(4,3.8808);// 急转弯阈值g
        setthreshold(5,2.1);// 轻微碰撞阈值g
        setthreshold(8,9);//急加速速度阈值g
        setthreshold(9,16.2);//急减速速度阈值g
    }
    //按保险数据要求设置的阈值
    /*
    急加速 harsh acceleration 车辆在纵向上的加速度大于2.5m/s2
    急减速 harsh deceleration车辆在纵向上的加速度小于-4.5m/s2
    急转弯 harsh turn车辆在横向上的加速度绝对值大于4m/s2且卫星定位方向改变量大于45°的事件。
    急变道 harsh lane-change车辆在横向上的加速度绝对值大于4m/s2且卫星定位方向改变量小于20°的事件。
    水平碰撞事故 horizontal collision车辆在纵向或横向上的加速度绝对值大于20m/s23.19 ，且车辆的俯仰角及侧倾角的绝对值均不大于20°的事件。
    翻转事故 rollover车辆的俯仰角或侧倾角的绝对值大于70°的事件。
    车辆稳定性报警 vehicle stability warning车辆在大于3s的时间内，持续以绝对值大于50°/s的角速度改变航向角的事件。
     */
public void setInsuranceTheshold()
{

    setthreshold(2,4.5);// 急刹车阈值
    setthreshold(3,2.5);// 急加速阈值
    setthreshold(4,4);// 急转弯阈值
    setthreshold(5,2.1);// 轻微碰撞阈值g
    setthreshold(8,9);//急加速速度阈值
    setthreshold(9,16.2);//急减速速度阈值
}
    //Call back from naitive background tasks
    /*

     */
    public void alarm_report(int alarmID, double jindu, double weidu, double hanxian) {
        String info = ", jindu:" + jindu + ", weidu:" + weidu + ", hanxian:" + hanxian;
       switch (alarmID) {
            case 0x12:
               // util.showAlert(getApplicationContext(), "碰撞" + info);
                alert = MediaPlayer.create(this, R.raw.huanzhuanbaojin);
               alert.start();
                //   util.saveManeuver(getApplicationContext(), "pengzhuang" + info);
                break;
            case 0x13:
               // util.showAlert(getApplicationContext(), "急加速" + info);
              alert = MediaPlayer.create(this, R.raw.jijiasu);
                alert.start();
                //   util.saveManeuver(getApplicationContext(), "jijiasu" + info);
                break;
            case 0x14:
               // util.showAlert(getApplicationContext(), "急减速" + info);
                alert = MediaPlayer.create(this, R.raw.jijiansu);
                alert.start();
                //   util.saveManeuver(getApplicationContext(), "jijisu" + info);
                break;
            case 0x15:
                //util.showAlert(getApplicationContext(), "翻转" + info);
                alert = MediaPlayer.create(this, R.raw.huanzhuanbaojin);
                alert.start();
               // util.saveManeuver(getApplicationContext(), "fanzhuan" + info);
                break;
            case 0x16:
                //util.showAlert(getApplicationContext(), "急转弯" + info);
                alert = MediaPlayer.create(this, R.raw.jizhuanwan);
                alert.start();
                //util.saveManeuver(getApplicationContext(), "jizhuanwan" + info);
                break;
        }


    }

    // On pause of the application, pause the sensor listener
    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
        try {
            locationManager.removeUpdates(locationListener);
        } catch (SecurityException e){

        }
        prefs.unregisterOnSharedPreferenceChangeListener(listener);

    }

    // On resume of the application, unpause the sensor listener
    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        } catch (SecurityException e){

        }
        prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    public class gpsListener implements LocationListener {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            int speed=(int) ((location.getSpeed()*3600)/1000);
            // add to gps checked
            double localatitude=location.getLatitude();
            double locallongtitude=location.getLongitude();
            double localcourse=location.getBearing();
            long localtime=location.getTime();
            ongpsdata( localatitude,locallongtitude, localcourse, localtime,speed);// call c
            checkSpeed(speed);

            if (chart.getSelectedAxis().equals("Speed")) {
                chart.addEntry(speed);
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    }

    /**
     * Smooths the data from the sensor
     * @param input
     * @param output
     * @return
     */
    protected float[] lowPassFilter( float[] input, float[] output ) {
        if (output == null) return input;

        for (int i=0; i<input.length; i++) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }

    /**
     * Verify if the speed surpassed the limit speed
     * @param speed
     *
     */
    private void checkSpeed(int speed) {
       // MediaPlayer mp;

        if (speed > SPEED_THRESHOLD){
            util.showAlert(this, "Alert: Excessive Speed");
           // mp = MediaPlayer.create(this, R.raw.warning_you_are_driving_too_fast);
           // mp.start();
        }
    }

    /**
     * Verify if acceleration surpassed the limit threshold for a dangerous acceleration
     * @param longAxisData
     */
    private void checkAcceleration(ArrayList<Float> longAxisData) {
        // check for dangerous acceleration or breaking
        boolean dangAcc = false;
        boolean dangBreak = false;

        for (int i = 0; i < longAxisData.size(); i++){
            if (longAxisData.get(i) < -ACC_THRESHOLD){
                dangBreak = true;
            } else if (longAxisData.get(i) > ACC_THRESHOLD){
                dangAcc = true;
            }
        }

        if (dangAcc){
            util.showAlert(this, "Alert: Excessive Acceleration");
           // alert  = MediaPlayer.create(this, R.raw.warning_aggressive_acceleration);
           // alert.start();
        } else if (dangBreak){
           // util.showAlert(this, "Alert: Sudden Break");
           // alert  = MediaPlayer.create(this, R.raw.warning_sudden_break);
          //  alert.start();
        }
    }

    /**
     * Update selected axis displayed on Chart
     * @param view
     */
    public void setAxis(View view){
        switch(view.getId()){
            case R.id.xAxis:
                chart.setSelectedAxis("xAxis");
                break;
            case R.id.yAxis:
                chart.setSelectedAxis("yAxis");
                break;
            case R.id.zAxis:
                chart.setSelectedAxis("zAxis");
                break;
            case R.id.speed:
                chart.setSelectedAxis("Speed");
                break;
        }
    }

    public void normalThread(View view) {
        //startharshdetection();
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native void readaccrdata( double accrx, double accry,double accrz);
    public native void startharshdetection();
   // public native void stopharshdetection();
    public native void ongpsdata(  double localatitude, double locallongtitude,  double localcourse,  float localgpstime,  int localgpsspeed);
    public native void setthreshold(int thresd_index,double alarm_thr );
    public native void setspeedtag(int speedtag);

}
