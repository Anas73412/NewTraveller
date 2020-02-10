package in.binplus.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.encoder.QRCode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import in.binplus.travel.QrGenerate.Contents;
import in.binplus.travel.QrGenerate.QRCodeEncoder;
import in.binplus.travel.util.ToastMsg;

public class ScanQrCodeActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG="GENERATEQRCODE";
    ImageView img_qr;
    Button btn_gen,btn_save;
    EditText edt_text;
    Bitmap bitmap;
    QRCodeEncoder qrgEncoder;

    private static final String IMAGE_DIRECTORY = "/TravellerQRCode";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_scan_qr_code );

        img_qr=(ImageView)findViewById(R.id.img_qr);
        edt_text=(EditText) findViewById(R.id.edt_text);
        btn_gen=(Button) findViewById(R.id.btn_gen);
        btn_save=(Button) findViewById(R.id.btn_save);
        btn_gen.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id=v.getId();

        if(id == R.id.btn_gen)
        {
            String str_qr=edt_text.getText().toString();
            if(str_qr.equals("") || str_qr.isEmpty())
            {
                new ToastMsg(ScanQrCodeActivity.this).toastIconError("Enter String");
            }
            else
            {
                generateQRCode(str_qr);
            }

        }
        else if(id == R.id.btn_save)
        {
            String se=saveImage(bitmap);
        }
    }

    private void generateQRCode(String str_qr) {

        WindowManager windowManager=(WindowManager) getSystemService(WINDOW_SERVICE);
        Display display=windowManager.getDefaultDisplay();
        Point point=new Point();
        display.getSize(point);
        int width=point.x;
        int height=point.y;
        int smallerDimension=width<height ? width : height;
        smallerDimension=smallerDimension*3/4;
        qrgEncoder=new QRCodeEncoder(str_qr,null, Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(),smallerDimension);
        try
        {
          bitmap=qrgEncoder.encodeAsBitmap();
          img_qr.setImageBitmap(bitmap);
        }
        catch (Exception ex)
        {
            Toast.makeText(ScanQrCodeActivity.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    //Save QR CODE
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress( Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY /*iDyme folder*/);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
            Log.d("hhhhh", wallpaperDirectory.toString());
        }
        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(ScanQrCodeActivity.this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
            Toast.makeText(this, "Signature Saved", Toast.LENGTH_SHORT).show();
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
            Toast.makeText(ScanQrCodeActivity.this,""+e1.getMessage(),Toast.LENGTH_LONG).show();
        }
        return "";

    }

}
