package fr.acinq.eclair.swordfish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import fr.acinq.eclair.swordfish.R;
import fr.acinq.eclair.swordfish.adapters.PaymentListItemAdapter;
import fr.acinq.eclair.swordfish.model.Payment;

public class ChannelActivity extends AppCompatActivity {

  //  public static final String EXTRA_MESSAGE = "fr.acinq.eclair.swordfish.MESSAGE";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_channel);
    PaymentListItemAdapter adapter = new PaymentListItemAdapter(this, Payment.listAll(Payment.class));
    ListView listView = (ListView) findViewById(R.id.main__listview_payments);
    listView.setAdapter(adapter);
  }

  public void channel_goToPayment(View view) {
    Intent intent = new Intent(this, PaymentActivity.class);
    startActivity(intent);
  }

  public void channel_goToChannelsList(View view) {
    Intent intent = new Intent(this, ChannelsListActivity.class);
    startActivity(intent);
  }

  public void channel_goToFund(View view) {
    Intent intent = new Intent(this, FundActivity.class);
    startActivity(intent);
  }

}