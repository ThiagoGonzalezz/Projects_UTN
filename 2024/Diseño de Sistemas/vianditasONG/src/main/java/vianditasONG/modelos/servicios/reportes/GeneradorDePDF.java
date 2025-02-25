package vianditasONG.modelos.servicios.reportes;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class GeneradorDePDF {

    public static String generarPDF(Reporte reporte) throws IOException {
        String nombreSanitizado = reporte.getNombre().replaceAll("\\s+", "_");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaSanitizada = reporte.getFecha().format(formatter);

        String rutaPdf = "uploads/reportes/" + nombreSanitizado + "_" + fechaSanitizada + ".pdf";
        File file = new File(rutaPdf);
        file.getParentFile().mkdirs();

        PdfWriter writer = new PdfWriter(rutaPdf);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont regular = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        Paragraph titulo = new Paragraph("Reporte: " + reporte.getNombre())
                .setFont(bold)
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
        document.add(titulo);

        Paragraph fecha = new Paragraph("Fecha: " + reporte.getFecha())
                .setFont(regular)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginBottom(10);
        document.add(fecha);

        Paragraph tipoReporte = new Paragraph("Tipo de Reporte: " + reporte.getTipoReporte())
                .setFont(regular)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginBottom(20);
        document.add(tipoReporte);

        for (String detalle : reporte.getDetalles()) {
            Paragraph detalleParrafo = new Paragraph(detalle)
                    .setFont(regular)
                    .setFontSize(12)
                    .setMarginBottom(5);
            document.add(detalleParrafo);
        }

        Paragraph pieDePagina = new Paragraph("Generado por vianditasONG")
                .setFont(regular)
                .setFontSize(10)
                .setFontColor(ColorConstants.GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                .setMarginTop(20);
        document.add(pieDePagina);

        document.close();
        return rutaPdf;
    }
}
