/* This is a rough version of a Augmentative Alternative Communication (AAC) Application for Android
   is currently very early and missing key functionality as well as prone to errors.
   It is meant to be mostly a proof of concept for a potential low-barrier of access speech therapy app.
   The ideal is to provide a simple tool that can be used and built upon in the future.
   *KNOWN ISSUES*
   * Styling and formatting causes some buttons/images to be pushed of screen
   * Text for dynamically created buttons is wrapped poorly for longer words
   * Longer words can be hard to read and awkward in size
   * Images are hard coded for now
   * Cannot set images to User Created buttons, can create blank buttons
   * UI buttons are hard coded
   * Dynamically created buttons are difficult to style
   * Created buttons also have no clear id, could lock memory when destroyed
   * Anonymous classes for the onClick event handlers could be assigned more effectively
   * All code is under MainActivity.Java, which is unnessacary.
   * tts function -speak, is deprecated, but used for older phones, No updated version set
   * Anything I missed
    This code was written by Patrick Trenery,last updated 6/28/2015, it is freeware.
 */

package com.example.patrick.texttospeech;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import java.util.Locale;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.graphics.drawable.Drawable;


public class MainActivity extends ActionBarActivity {

    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateUI(); //creates key buttons such as the text box
        generateLibrary(); //generates pre-made buttons with images for default functionality
        tts = new TextToSpeech(getApplicationContext(), //tts is text to speak object
                new TextToSpeech.OnInitListener() {@Override
                                                   public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        tts.setLanguage(Locale.US); //Locale == dialect
                    }
                }
                });
    }

    @Override
    public void onPause() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            //to prevent it from hanging.
        }
        super.onPause();

    }

    private void generateLibrary() { //A function designed to load a selected few pre-made buttons into the library.
        //Generated here to so new libraries can be introduced and regenerated.
        //Future versions should share onclick handlers and be generated dynamically for best results
        final EditText edittxt = (EditText) findViewById(R.id.editText);
        final Button wantBtn = new Button(getApplicationContext());
        final Button youBtn = new Button(getApplicationContext());
        final Button meBtn = new Button(getApplicationContext());
        final Button doBtn = new Button(getApplicationContext());

        doBtn.setText("Do");
        Drawable doPic = getResources().getDrawable(R.drawable.doimg);
        doBtn.setCompoundDrawablesWithIntrinsicBounds(null, doPic, null, null);

        doBtn.setTextColor(Color.BLACK);
        GridLayout grid = (GridLayout) findViewById(R.id.grid);
        grid.addView(doBtn);

        doBtn.setOnClickListener(new View.OnClickListener() {@Override
                                                             public void onClick(View v) {
            String addTxt = doBtn.getText().toString();
            edittxt.append(addTxt);
            tts.speak(addTxt, TextToSpeech.QUEUE_FLUSH, null);
        }
        });



        wantBtn.setText("Want");
        Drawable wantPic = getResources().getDrawable(R.drawable.wantimg);
        wantBtn.setCompoundDrawablesWithIntrinsicBounds(null, wantPic, null, null);

        wantBtn.setTextColor(Color.BLACK);

        grid.addView(wantBtn);

        wantBtn.setOnClickListener(new View.OnClickListener() {@Override
                                                               public void onClick(View v) {
            String addTxt = wantBtn.getText().toString();
            edittxt.append(addTxt);
            tts.speak(addTxt, TextToSpeech.QUEUE_FLUSH, null);
        }
        });

        youBtn.setText("You");
        Drawable youPic = getResources().getDrawable(R.drawable.youimg);
        youBtn.setCompoundDrawablesWithIntrinsicBounds(null, youPic, null, null);

        youBtn.setTextColor(Color.BLACK);

        grid.addView(youBtn);

        youBtn.setOnClickListener(new View.OnClickListener() {@Override
                                                              public void onClick(View v) {
            String addTxt = youBtn.getText().toString();
            edittxt.append(addTxt);
            tts.speak(addTxt, TextToSpeech.QUEUE_FLUSH, null);
        }
        });
        meBtn.setText("Me");
        Drawable mePic = getResources().getDrawable(R.drawable.meimg);
        meBtn.setCompoundDrawablesWithIntrinsicBounds(null, mePic, null, null);

        meBtn.setTextColor(Color.BLACK);

        grid.addView(meBtn);

        meBtn.setOnClickListener(new View.OnClickListener() {@Override
                                                             public void onClick(View v) {
            String addTxt = meBtn.getText().toString();
            edittxt.append(addTxt);
            tts.speak(addTxt, TextToSpeech.QUEUE_FLUSH, null);

        }
        });



    }

    private void generateUI() {
        // Creates the key user interface buttons
        final Button speakBtn = (Button) findViewById(R.id.speakBtn);
        final EditText edittxt = (EditText) findViewById(R.id.editText);
        final Button createButton = (Button) findViewById(R.id.createBtn);
        final Button removeButton = (Button) findViewById(R.id.removeBtn);


        speakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Message function using text to speech tool, grabs text from textbox "edittxt"
                String text = edittxt.getText().toString();
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                //tts is the object of the text to speech tool, speak is technically depreciated,
                //but is useful here because it allows for best compatibility with older phones
            }
        });


        removeButton.setOnClickListener(new View.OnClickListener() {@Override
                                                                    public void onClick(View v) {
            //A clear button to remove all buttons, future version should leave library buttons
            GridLayout grid = (GridLayout) findViewById(R.id.grid);
            grid.removeAllViews();
        }
        });
        createButton.setOnClickListener(new View.OnClickListener() {@Override
                                                                    public void onClick(View v) {
            //As of now, there is a Error involving making blank button
            //dynamically created buttons may not have a id, which might cause memory leaks on
            //deletion.
            final Button myButton = new Button(getApplicationContext());
            String grabCurrentText = edittxt.getText().toString();
            myButton.setText(grabCurrentText);
            myButton.setTextColor(Color.BLACK);
            GridLayout grid = (GridLayout) findViewById(R.id.grid);
            grid.addView(myButton);
            //setting max size of created buttons to prevent long words from being too large
            myButton.setMaxWidth(140);
            myButton.setMaxHeight(140);
            //This is the event handlered that is created for the new button, which allows
            //for the text to be added and then voiced
            myButton.setOnClickListener(new View.OnClickListener() {@Override
                                                                    public void onClick(View v) {
                String addTxt = myButton.getText().toString();
                edittxt.append(addTxt);
                tts.speak(addTxt, TextToSpeech.QUEUE_FLUSH, null);

            }
            });


        }
        });

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}