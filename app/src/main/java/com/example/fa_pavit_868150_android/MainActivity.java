package com.example.fa_pavit_868150_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fa_pavit_868150_android.Product;
import com.example.fa_pavit_868150_android.ProductDAO;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddProductFragment.OnProductAddedListener {

    private ListView listViewProducts;
    private ImageView imageViewAdd;
    private ProductDAO productDAO;
    private List<Product> products;
    private ArrayAdapter<Product> adapter;
    private TextView title;
    private SearchView searchh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productDAO = new ProductDAO(this);
        products = new ArrayList<>();

        listViewProducts = findViewById(R.id.listViewProducts);
        imageViewAdd = findViewById(R.id.imageViewAdd);
        title = findViewById(R.id.title);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, products);
        listViewProducts.setAdapter(adapter);

        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listViewProducts.setVisibility(View.GONE);
                // Show the Add Product fragment
                imageViewAdd.setVisibility(View.GONE);
                title.setVisibility(View.GONE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer, new AddProductFragment());
                fragmentTransaction.commit();
            }
        });

        listViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Product product = products.get(position);
                Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
                intent.putExtra("product", (CharSequence) product);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Get the search view and set the searchable configuration
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the list of products based on the search query
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }

    private void loadProducts() {
        products = productDAO.getAllProducts();
        adapter.clear();
        adapter.addAll(products);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onProductAdded(Product product) {
        productDAO.insertProduct(product);
        loadProducts();
        listViewProducts.setVisibility(View.VISIBLE);
        imageViewAdd.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
    }
}