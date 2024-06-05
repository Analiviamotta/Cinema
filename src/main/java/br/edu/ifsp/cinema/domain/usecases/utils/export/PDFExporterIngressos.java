package br.edu.ifsp.cinema.domain.usecases.utils.export;

import br.edu.ifsp.cinema.domain.entities.ingresso.Ingresso;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PDFExporterIngressos implements Exportable<Ingresso> {
    private String filePath;

    public PDFExporterIngressos(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void generatesExportableReport(List<Ingresso> ingressos) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph("Ingressos"));
            document.add(new Paragraph(" "));

            for (Ingresso ingresso: ingressos) {
                Duration duration = ingresso.getSessaoExibicao().getTempoDuracao();
                long hours = duration.toHours();
                long minutes = duration.minus(hours, ChronoUnit.HOURS).toMinutes();
                long seconds = duration.minus(hours, ChronoUnit.HOURS)
                        .minus(minutes, ChronoUnit.MINUTES)
                        .getSeconds();

                document.add(new Paragraph("Assento: " + ingresso.getAssento().getColuna() + "-" + ingresso.getAssento().getLinha()));
                document.add(new Paragraph("Sessão/Exibição: "  + ingresso.getSessaoExibicao().getFilme().getTitulo() + " | Duração: " + String.format("%02d:%02d:%02d", hours, minutes, seconds)));
                document.add(new Paragraph("Preço: R$" + ingresso.getPreco()));
                document.add(new Paragraph("--------------------------------------------------------------------------------------------------------------------------------"));
            }

            document.close();
            System.out.println("PDF gerado com sucesso em: " + filePath);
        } catch (DocumentException | FileNotFoundException e) {
            System.err.println("Erro ao gerar o PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
