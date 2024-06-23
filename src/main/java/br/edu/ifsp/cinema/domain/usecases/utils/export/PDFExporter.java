package br.edu.ifsp.cinema.domain.usecases.utils.export;

import br.edu.ifsp.cinema.domain.entities.venda.Venda;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class PDFExporter implements Exportable<Venda> {

    private String filePath;

    public PDFExporter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void generatesExportableReport(List<Venda> vendas) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph("Relatório de Vendas"));

            for (Venda venda : vendas) {
                document.add(new Paragraph("ID da Venda: " + venda.getId()));
                document.add(new Paragraph("Data da Venda: " + venda.getData().toString()));
                document.add(new Paragraph("Preço Total: " + venda.getPrecoTotal().toString()));
                document.add(new Paragraph(" "));
            }

            document.close();
            System.out.println("PDF gerado com sucesso em: " + filePath);
        } catch (DocumentException | FileNotFoundException e) {
            System.err.println("Erro ao gerar o PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
