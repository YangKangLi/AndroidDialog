package cn.com.ziquan.libraries;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.com.ziquan.lib.dialog.DialogAgent;
import cn.com.ziquan.lib.dialog.LibDialog;

public class MainActivity extends AppCompatActivity {

    private DialogAgent dialogAgent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_test_alert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAgent.getInstance(MainActivity.this).create(LibDialog.DIALOG_TYPE_ALERT).setContent("演示警告对话框")
                        .setPositiveBtn(new LibDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogAgent agent, int which) {
                                agent.dismiss();
                            }
                        }).showDialog();
            }
        });

        findViewById(R.id.btn_test_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAgent.getInstance(MainActivity.this).create(LibDialog.DIALOG_TYPE_CONFIRM).setContent("演示确认对话框？")
                        .setPositiveBtn(new LibDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogAgent agent, int which) {
                                agent.dismiss();
                                Toast.makeText(MainActivity.this, "您点击了确认按钮", Toast.LENGTH_LONG).show();
                            }
                        }).showDialog();
            }
        });

        findViewById(R.id.btn_test_waiting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAgent = DialogAgent.getInstance(MainActivity.this).create(LibDialog.DIALOG_TYPE_WAITING).setContent("正在加载数据，请稍等")
                        .showDialog();
                // 开启线程，5秒钟后关闭等待对话框
                new Thread(mRunnable).start();
            }
        });
    }

    private Runnable mRunnable = new Runnable() {

        private int count;

        @Override
        public void run() {
            count = 5;
            do {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (count <= 0) {
                            dialogAgent.dismiss();
                        } else {
                            dialogAgent.updateContent(count + "秒后关闭对话框");
                        }

                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count--;
            } while (count >= 0);
        }
    };
}
