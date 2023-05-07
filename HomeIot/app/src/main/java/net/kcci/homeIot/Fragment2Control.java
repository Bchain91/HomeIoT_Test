package net.kcci.homeIot;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class Fragment2Control extends Fragment {
    MainActivity mainActivity;
    Button buttonCondition;
    Button buttonControl;
    TextView textViewIllumination;
    TextView textViewTemper;
    TextView textViewHumi;
    ImageView imageViewLed;
    ImageView imageViewBlind;
    ImageView imageViewAir;
    Switch blindSwitch;
    Switch ledSwitch;
    Switch airSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        imageViewLed = view.findViewById(R.id.imageViewLed);
        imageViewBlind = view.findViewById(R.id.imageViewBlind);
        imageViewAir = view.findViewById(R.id.imageViewAir);
        buttonCondition = view.findViewById(R.id.buttonCondition);
        buttonControl = view.findViewById(R.id.buttonControl);
        textViewIllumination = view.findViewById(R.id.textViewIllumination);
        textViewTemper = view.findViewById(R.id.textViewTemper);
        textViewHumi = view.findViewById(R.id.textViewHumi);
        mainActivity = (MainActivity) getActivity();
        ledSwitch = view.findViewById(R.id.switchLed);
        blindSwitch = view.findViewById(R.id.switchBlind);
        airSwitch = view.findViewById(R.id.switchAir);

        buttonCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ClientThread.socket != null) {
                    mainActivity.clientThread.sendData("[BCI_ARD]GETSENSOR\n");
                }
            }
        });
        ledSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ClientThread.socket != null)
                    if (ledSwitch.isChecked()) {
                        mainActivity.clientThread.sendData("LEDON\n");
                        ledSwitch.setChecked(false);
                    } else {
                        mainActivity.clientThread.sendData("LEDOFF\n");
                        ledSwitch.setChecked(true);
                    }
            }
        });

        blindSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ClientThread.socket != null)
                    if (blindSwitch.isChecked()) {
                        mainActivity.clientThread.sendData("BLINDON\n");
                        blindSwitch.setChecked(false);
                    } else {
                        mainActivity.clientThread.sendData("BLINDOFF\n");
                        blindSwitch.setChecked(true);
                    }
            }
        });

        airSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ClientThread.socket != null)
                    if (airSwitch.isChecked()) {
                        mainActivity.clientThread.sendData("AIRON\n");
                        airSwitch.setChecked(false);
                    } else {
                        mainActivity.clientThread.sendData("AIROFF\n");
                        airSwitch.setChecked(true);
                    }
            }
        });

        return view;
    }

    void recvDataProcess(String strRecvData) {
        String[] splitLists = strRecvData.toString().split("\\[|]|@|\\r");
        for (int i = 0; i < splitLists.length; i++)
            Log.d("recvDataProcess", "i : " + i + ", vlaue : " + splitLists[i]);

        if (splitLists[2].equals("SENSOR")) {
            textViewIllumination.setText(splitLists[3] + "%");
            textViewTemper.setText(splitLists[4] + "C");
            textViewHumi.setText(splitLists[5] + "%");
        } else if (splitLists[2].equals("LEDON")) {
            imageViewLed.setImageResource(R.drawable.led_on);
            ledSwitch.setChecked(true);
        } else if (splitLists[2].equals("LEDOFF")) {
            imageViewLed.setImageResource(R.drawable.led_off);
            ledSwitch.setChecked(false);
        } else if (splitLists[2].equals("BLINDON")) {
            imageViewBlind.setImageResource(R.drawable.blinds_on);
            blindSwitch.setChecked(true);
        } else if (splitLists[2].equals("BLINDOFF")) {
            imageViewBlind.setImageResource(R.drawable.blinds_off);
            blindSwitch.setChecked(false);
        } else if (splitLists[2].equals("AIRON")) {
            imageViewAir.setImageResource(R.drawable.air_on);
            airSwitch.setChecked(true);
        } else if (splitLists[2].equals("AIROFF")) {
            imageViewAir.setImageResource(R.drawable.air_off);
            airSwitch.setChecked(false);
        }
    }
}




