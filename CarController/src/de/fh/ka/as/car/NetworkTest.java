package de.fh.ka.as.car;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import de.fh.ka.as.car.model.SomeRequest;

public class NetworkTest extends Activity {
	/** Called when the activity is first created. */

	private Button startServer, send, connect;
	private Client client;
	private Server server;
	private Kryo kryo;
	private EditText textField;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.networktest);
		connect = (Button) findViewById(R.id.connect);
		if (connect != null) {
			connect.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					startClient();
				}
			});
		}
		;
		send = (Button) findViewById(R.id.Send);
		if (send != null) {
			send.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					sendData();
				}
			});
		}
		startServer = (Button) findViewById(R.id.server);
		if (startServer != null) {
			startServer.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					startServer();
				}
			});
		}
		textField = (EditText) findViewById(R.id.textfield);
	}

	public void startClient() {
		client = new Client();
		client.start();
		try {
			client.connect(5000, "192.168.1.80", 54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}

		kryo = client.getKryo();
		kryo.register(SomeRequest.class);

	}

	public void sendData() {

		SomeRequest request = new SomeRequest(textField.getText().toString());
		client.sendTCP(request);
	}

	public void startServer() {
		server = new Server();
		server.start();
		try {
			server.bind(54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}

		kryo = server.getKryo();
		kryo.register(SomeRequest.class);

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof SomeRequest) {
					SomeRequest request = (SomeRequest) object;

					// textField.setText(request.toString());
					Log.i("Network", request.toString());
					// SomeRequest response = new SomeRequest("Thanks!");
					// connection.sendTCP(response);
				}
			}
		});

	}
}