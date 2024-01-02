package edu.cs.birzeit.assi2;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
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

public class SchoolActivity extends AppCompatActivity {
    private final String url = "https://code.org/schools.json";
    private EditText edtCountry;
    private RequestQueue queue;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);
        edtCountry = findViewById(R.id.edtCountry);
        txtResult = findViewById(R.id.txtResult);
        queue = Volley.newRequestQueue(this);
    }

    public void btnShow_Click(View view) {
        String country = edtCountry.getText().toString();
        if (country.isEmpty()) {
            txtResult.setText("Enter Country name");
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = "";
                            try {
                                JSONObject jsonRootObject = new JSONObject(response);
                                JSONArray schoolsArray = jsonRootObject.getJSONArray("schools");

                                for (int i = 0; i < schoolsArray.length(); i++) {
                                    JSONObject schoolObj = schoolsArray.getJSONObject(i);
                                    String schoolCountry = schoolObj.optString("country", "");

                                    if (country.equalsIgnoreCase(schoolCountry)) {
                                        String schoolName = schoolObj.optString("name", "");
                                        String schoolWebsite = schoolObj.optString("website", "");

                                        result += "School Name: " + schoolName + "\n";
                                        result += "Website: " + schoolWebsite + "\n\n";
                                    }
                                }

                                if (result.isEmpty()) {
                                    result = "No schools found in the specified country.";
                                }

                                txtResult.setText(result);
                                // Close keyboard
                                InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                input.hideSoftInputFromWindow(view.getWindowToken(), 0);

                            } catch (JSONException exception) {
                                exception.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SchoolActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }
}