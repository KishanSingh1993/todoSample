package in.ktechnos.todoassignment.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import in.ktechnos.todoassignment.R;
import in.ktechnos.todoassignment.adapter.RecyclerAdaptor;

public class SelectImages extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    TextView textView;
    Button button;
    ArrayList<Uri> list;
    RecyclerAdaptor adaptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_images);

        list=new ArrayList<>();
        recyclerView=findViewById(R.id.recycler);

        adaptor=new RecyclerAdaptor(list);
        recyclerView.setLayoutManager(new GridLayoutManager(SelectImages.this,4));
        recyclerView.setAdapter(adaptor);

        openGalley();
    }

    private void openGalley() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Selcet Picture"),123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123 && resultCode==RESULT_OK){
            if(data.getClipData()!=null){
                int x=data.getClipData().getItemCount();
                for(int i=0;i<x;i++){
                    list.add(data.getClipData().getItemAt(i).getUri());
                }
                adaptor.notifyDataSetChanged();
                textView.setText("Image("+list.size()+")");
            }else if(data.getData()!=null){
                String imgurl=data.getData().getPath();
                list.add(Uri.parse(imgurl));
            }
        }
    }

    @Override
    public void onClick(View v) {

    }
}