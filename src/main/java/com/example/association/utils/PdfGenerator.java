package com.example.association.utils;

import com.example.association.models.Don;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;

@Component
public class PdfGenerator {

    public static File generateRecuPdf(Don don) throws Exception {
        Document document = new Document();
        File pdfFile = File.createTempFile("recu_don_", ".pdf");
        PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph title = new Paragraph("Reçu de Don", font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" ")); // espace

        document.add(new Paragraph("Nom : " + don.getNom()));
        document.add(new Paragraph("Email : " + don.getEmail()));
        document.add(new Paragraph("Montant : " + don.getMontant() + " EUR"));
        document.add(new Paragraph("Type de don : " + don.getTypeDon()));
        document.add(new Paragraph("Mode de paiement : " + don.getPaiement()));
        document.add(new Paragraph("Date du don : " + don.getDateDon()));

        document.close();
        return pdfFile;
    }
}
