package dev.dotworld.modbus.sample;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zgkxzx.modbus4And.ModbusFactory;
import com.zgkxzx.modbus4And.ModbusMaster;
import com.zgkxzx.modbus4And.exception.ModbusInitException;
import com.zgkxzx.modbus4And.msg.ReadHoldingRegistersRequest;
import com.zgkxzx.modbus4And.msg.ReadHoldingRegistersResponse;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView list;
    private Button write, read;

    private static ModbusMaster master;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        list = findViewById(R.id.list);
        write = findViewById(R.id.write);
        read = findViewById(R.id.read);


        try {
            master = new ModbusFactory().createRtuMaster(new AndroidSPWrapper("/dev/ttyS4"));
            master.setTimeout(50);
            master.setRetries(0);
            master.init();
            list.setText("Modbus init success");
        } catch (ModbusInitException e) {
            list.setText("Modbus init exception.\n" + e.getMessage());
            e.printStackTrace();
        }

        ApplicationInfo ainfo = null;
        try {
            ainfo = this.getApplicationContext().getPackageManager().getApplicationInfo
                    (
                            getPackageName(),
                            PackageManager.GET_SHARED_LIBRARY_FILES
                    );
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        SerialPortFinder spf = new SerialPortFinder();
        String[] paths = spf.getAllDevicesPath();

        Arrays.stream(paths).forEach(path -> {
            Log.i(TAG, "onCreate: Path : " + path);
        });

        Log.v(TAG, "native library dir " + ainfo.nativeLibraryDir);


        read.setOnClickListener(v -> {
            list.setText("");
            read.setEnabled(false);

            try {
                if (master == null || !master.isInitialized()) {
                    Log.i(TAG, "onCreate: Master not initialized");
                    list.setText("Mobbus doesn't seems to be initialized");
                    return;
                }
                int slaveId = 1;

                ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, 1, 1);
                ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) master.send(request);

                if (response.isException()) {
                    list.setText("Exception : " + response.getExceptionMessage());
                    Log.i(TAG, "onCreate: Exception");
                } else {
                    list.setText("Got Data : " + Arrays.toString(response.getData()));
                    Log.i(TAG, "onCreate: Got Data : " + Arrays.toString(response.getData()));
                }
            } catch (Exception e) {
                e.printStackTrace();
                list.setText("Exception : " + e.getMessage());
            }
            read.setEnabled(true);
        });
    }
}