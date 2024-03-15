package com.example.tienda.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.List;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.poi.ss.usermodel.Sheet;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.example.tienda.Model.DTO.Proveedor.ProveedorDTO;
import com.example.tienda.Model.Entity.ProveedorEntity;
import com.example.tienda.Model.Service.IProveedor;
import com.example.tienda.Payload.MessageResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;

import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;

import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import com.lowagie.text.pdf.PdfWriter;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProveedorController {

    @Autowired
    private IProveedor service;

    @PreAuthorize("hasAuthority('READ_ALL_PROVEEDOR')")
    @GetMapping("/proveedor")
    public ResponseEntity<?> getAllProveedorPartial() {
        try {
            List<ProveedorDTO> listaProveedor = service.findAll();

            if (listaProveedor.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No se encontraron Proveedores")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Lista de Proveedores")
                        .object(listaProveedor)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasAuthority('READ_ALL_ACTIVE_PROVEEDOR')")
    @GetMapping("/proveedor/activo")
    public ResponseEntity<?> getAllProveedorPartialActive() {
        try {
            List<ProveedorDTO> listaActivos = service.findAllActive();
            if (listaActivos.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No hay Proveedores activos")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Lista de Proveedores Activos")
                        .object(listaActivos)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasAuthority('READ_ALL_INACTIVE_PROVEEDOR')")
    @GetMapping("/proveedor/inactivo")
    public ResponseEntity<?> getAllProveedorPartialInactive() {
        try {
            List<ProveedorDTO> listaInactivos = service.findAllInactive();
            if (listaInactivos.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No hay registros Inactivos")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Lista de Proveedores Inactivos")
                        .object(listaInactivos)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('SEARCH_ID_PROVEEDOR')")
    @GetMapping("/proveedor/{id}")
    public ResponseEntity<?> getFindByIdProveedor(@PathVariable Integer id) {
        try {
            ProveedorEntity idProveedor = service.findById(id);
            if (idProveedor == null) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No se encontro el ID del proveedor")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);

            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Resultado de la busqueda")
                        .object(idProveedor)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('SEARCH_NAME_PROVEEDOR')")
    @GetMapping("/proveedor/buscar/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
        try {
            List<ProveedorEntity> lista = service.buscarPorNombre(nombre);

            if (lista.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No hay registros")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Resultado de la busqueda")
                        .object(lista)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PreAuthorize("hasAuthority('SAVE_PROVEEDOR')")
    @PostMapping("/proveedor")
    public ResponseEntity<?> saveProveedor(@Valid @RequestBody ProveedorEntity proveedor) {
        ProveedorEntity saveProveedor = null;
        try {
            saveProveedor = service.save(proveedor);
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Guardado Exitosamente")
                    .object(saveProveedor)
                    .build(), HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(e)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('EDIT_PROVEEDOR')")
    @PutMapping("/proveedor/{id}")
    public ResponseEntity<?> updateProveedor(@Valid @RequestBody ProveedorEntity proveedor, @PathVariable Integer id) {
        ProveedorEntity updateProveedor = null;
        try {
            ProveedorEntity proveedorId = service.findById(id);
            if (proveedorId != null) {
                updateProveedor = service.save(proveedor);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Proveedor Actualizado")
                        .object(updateProveedor)
                        .build(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No se encontro el Proveedor")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }

        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('CHANGE_ACTIVIDAD_PROVEEDOR')")
    @PatchMapping("/proveedor/{id_provider}")
    public ResponseEntity<?> changeOfStatus(@PathVariable Integer id_provider) {
        try {
            ProveedorEntity providerEntity = service.findById(id_provider);
            if (providerEntity != null) {
                ProveedorEntity providerChange = service.changeofState(providerEntity);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Estado actualizado")
                        .object(providerChange)
                        .build(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Id no encontrado")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('DELETE_PROVEEDOR')")
    @DeleteMapping("/proveedor/{id}")
    public ResponseEntity<?> deleteProveeedor(@PathVariable Integer id) {
        try {
            ProveedorEntity providerEntity = service.findById(id);
            if (providerEntity != null) {
                service.delete(providerEntity);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Eliminado Correctamente")
                        .object(null)
                        .build(), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Id no encontrado")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('REPORT_PDF_PROVEDOR')")
    @GetMapping("/proveedor/reporte/pdf")
    public ResponseEntity<?> generarReportePdf() {
        try {

            List<ProveedorDTO> proveedores = service.findAll();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, baos);
            document.open();

            Paragraph title = new Paragraph("Reporte de Proveedores",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(80);
            table.setSpacingBefore(20);

            Stream.of("Codigo", "Nombre", "Telefono", "Ruc").forEach(columnTitle -> {
                PdfPCell header = new PdfPCell();
                header.setBorderWidth(2);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setPhrase(new Phrase(columnTitle, FontFactory.getFont(FontFactory.TIMES_BOLD, 16)));
                table.addCell(header);
            });

            // Celdas de datos
            for (ProveedorDTO proveedor : proveedores) {
                table.addCell(proveedor.getCodigo());
                table.addCell(proveedor.getNombre());
                table.addCell(proveedor.getTelefono());
                table.addCell(proveedor.getRuc());
            }

            document.add(table);
            document.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("inline").filename("Proveedor.pdf").build());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(baos.toByteArray());

        } catch (DocumentException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('REPORT_EXCEL_PROVEEDOR')")
    @GetMapping("/proveedor/reporte/excel")
    public ResponseEntity<byte[]> generarReporteExcel() throws IOException {
        try {
            List<ProveedorDTO> proveedores = service.findAll();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Proveedor");

            // Crear el estilo para el título
            Font titleFont = workbook.createFont();
            titleFont.setFontHeightInPoints((short) 20);
            titleFont.setBold(true);
            titleFont.setColor(IndexedColors.WHITE.getIndex());

            CellStyle titleCellStyle = workbook.createCellStyle();
            titleCellStyle.setFont(titleFont);
            titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
            titleCellStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
            titleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Crear el estilo para la celda de información
            CellStyle infoCellStyle = workbook.createCellStyle();
            infoCellStyle.setAlignment(HorizontalAlignment.CENTER);

            // Agregar la imagen del logo
            CreationHelper helper = workbook.getCreationHelper();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(0);
            anchor.setRow1(0);
            anchor.setCol2(3);
            anchor.setRow2(3);
            anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);

            // Agregar el título
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(4);
            titleCell.setCellValue("REPORTE DE PROVEEDORES");
            titleCell.setCellStyle(titleCellStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 7));

            // Agregar la lista de proveedores
            Row headerRow = sheet.createRow(2);
            headerRow.createCell(4).setCellValue("Codigo");
            headerRow.createCell(5).setCellValue("Nombre");
            headerRow.createCell(6).setCellValue("Teléfono");
            headerRow.createCell(7).setCellValue("Ruc");

            // Aplicar estilos a la cabecera
            for (Cell headerCell : headerRow) {
                headerCell.setCellStyle(titleCellStyle);
            }

            // Agregar datos de los proveedores
            int rowNum = 3; // Comenzar después de la cabecera
            for (ProveedorDTO proveedor : proveedores) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(4).setCellValue(proveedor.getCodigo());
                row.createCell(5).setCellValue(proveedor.getNombre());
                row.createCell(6).setCellValue(proveedor.getTelefono());
                row.createCell(7).setCellValue(proveedor.getRuc());

                // Aplicar estilos a los datos
                for (Cell cell : row) {
                    cell.setCellStyle(infoCellStyle);
                }
            }

            // Convertir el libro de trabajo a bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            // Configurar las cabeceras de la respuesta HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(
                    MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("filename", "reporte_proveedores.xlsx");
            headers.setContentLength(outputStream.size());

            // Retornar la respuesta con el archivo Excel
            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
