package layouting.aplikasi.com.filesystem;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks{

    EditText edtTitle, edtContent;
    Button btNew, btOpen, btSave;

    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTitle = findViewById(R.id.edt_file_title);
        edtContent = findViewById(R.id.edt_file_content);
        btNew = findViewById(R.id.bt_new);
        btOpen = findViewById(R.id.bt_open);
        btSave = findViewById(R.id.bt_save);

        path = getFilesDir();

        btOpen.setOnClickListener(this);
        btNew.setOnClickListener(this);
        btSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_open:
                openFile();
                break;
            case R.id.bt_new:
                newFile();
                break;
            case R.id.bt_save:
                saveFile();
                break;
        }

    }

    private void saveFile() {
        if (edtTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title must be filled", Toast.LENGTH_SHORT).show();
        } else {
            String title = edtTitle.getText().toString();
            String content = edtContent.getText().toString();
            FileHelper.writeFile(title, content, this);

            Toast.makeText(this, "Saving file", Toast.LENGTH_SHORT).show();
        }
    }

    private void newFile() {
        edtTitle.setText("");
        edtContent.setText("");

        Toast.makeText(this, "Clearing File", Toast.LENGTH_SHORT).show();
    }

    private void openFile() {
        showList();
    }

    private void showList() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String file : path.list()) {
            arrayList.add(file);
        }

        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert
                .setTitle("Pilih File Yang Di Inginkan")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadData(items[which].toString());
                    }
                });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    private void loadData(String title) {
        String text = FileHelper.readFromFile(this, title);
        edtTitle.setText(title);
        edtContent.setText(text);
        Toast.makeText(this, "Loading " + title + " data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}
