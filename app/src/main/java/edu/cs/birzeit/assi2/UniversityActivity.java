package edu.cs.birzeit.assi2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.io.StringWriter;
import java.text.DecimalFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class UniversityActivity extends AppCompatActivity {
    private final String url = "http://universities.hipolabs.com/search";
    private EditText edtCountry;
    private RequestQueue queue;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);
        edtCountry = findViewById(R.id.edtCountry);
        txtResult = findViewById(R.id.txtResult);
        queue = Volley.newRequestQueue(this);
    }

    public void btnShow_Click(View view) {
        String country = edtCountry.getText().toString();
        if (country.equals("")) {
            txtResult.setText("Enter Country name");
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = "";
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject universityObj = jsonArray.getJSONObject(i);
                                    if (country.equalsIgnoreCase(universityObj.getString("country"))) {
                                        String name = universityObj.getString("name");
                                        JSONArray domainsArray = universityObj.getJSONArray("domains");
                                        result += "University Name: " + name + "\n";
                                        result += "Domains: " + domainsArray.toString() + "\n\n";
                                    }
                                }

                                if (result.isEmpty()) {
                                    result = "No universities found in the specified country.";
                                }

                                txtResult.setText(result);
                                //close keyboard
                                InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                input.hideSoftInputFromWindow(view.getWindowToken(), 0);

                            } catch (JSONException exception) {
                                exception.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(UniversityActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }
}