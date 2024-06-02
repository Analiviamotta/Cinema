package br.edu.ifsp.cinema.domain.usecases.utils.export;

import java.util.List;

public interface Exportable<T> {
    void generatesExportableReport(List<T> entities);
}
