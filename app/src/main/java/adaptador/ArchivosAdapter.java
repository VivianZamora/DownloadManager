package adaptador;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.downloadmanager.R;

import java.io.File;
import java.util.List;

import modelo.Archivos;

public class ArchivosAdapter extends RecyclerView.Adapter<ArchivosAdapter.ViewHolder> {

    private List<Archivos> listaArchivos;
    private LayoutInflater lInflater;
    private Context context;
    public int cont;

    public ArchivosAdapter(List<Archivos> listaArchivos, Context context) {
        this.listaArchivos = listaArchivos;
        this.lInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return listaArchivos.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = lInflater.inflate(R.layout.layout_item, null);
        ViewHolder holder = new ArchivosAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ArchivosAdapter.ViewHolder holder, final int position) {
        holder.bindData(listaArchivos.get(position));
    }

    public List<Archivos> getListaArchivos() {
        return listaArchivos;
    }

    public void setListaArchivos(List<Archivos> listaArchivos) {
        this.listaArchivos = listaArchivos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView lblNombre, lblFichero, lblFecha;
        public long descarga;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            lblNombre = itemView.findViewById(R.id.lblNombre);
            lblFichero = itemView.findViewById(R.id.lblFichero);
            lblFecha = itemView.findViewById(R.id.lblFecha);
        }

        private void bindData(final Archivos archivo) {
            lblNombre.setText(archivo.getNombre());
            lblFichero.setText("fichero: " + (++cont));
            lblFecha.setText(archivo.getFecha());

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    File file=new File(context.getExternalFilesDir(null),archivo.getNombre());

                    DownloadManager.Request request;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        request = new DownloadManager.Request(Uri.parse(archivo.getUrl()))
                                .setTitle(archivo.getNombre())
                                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                .setDestinationUri(Uri.fromFile(file))
                                .setRequiresCharging(false)
                                .setAllowedOverMetered(true)
                                .setAllowedOverRoaming(true);
                    } else {
                        request = new DownloadManager.Request(Uri.parse(archivo.getUrl()))
                                .setTitle(archivo.getNombre())
                                .setDescription("Downloading")
                                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                .setDestinationUri(Uri.fromFile(file))
                                .setAllowedOverRoaming(true);
                    }

                    DownloadManager downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
                    descarga = downloadManager.enqueue(request);

                }
            });
        }
    }
}