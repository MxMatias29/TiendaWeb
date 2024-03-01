package com.example.tienda.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;
import java.io.ByteArrayOutputStream;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tienda.Model.DTO.Producto.ProductoDTO;
import com.example.tienda.Model.Entity.ProductoEntity;
import com.example.tienda.Model.Service.IProducto;
import com.example.tienda.Payload.MessageResponse;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProductoController {

    @Autowired
    private IProducto service;

    @GetMapping("/producto")
    public ResponseEntity<?> getAllProductoPartial() {
        try {
            List<ProductoDTO> listaProducto = service.findAll();

            if (listaProducto.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No se encontraron Productos")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Lista de Productos")
                        .object(listaProducto)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/producto/activo")
    public ResponseEntity<?> getAllProductoPartialActive() {
        try {
            List<ProductoDTO> listaActivos = service.findAllActive();
            if (listaActivos.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No hay Productos activos")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Lista de Productos Activos")
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

    @GetMapping("/producto/inactivo")
    public ResponseEntity<?> getAllProductoPartialInactive() {
        try {
            List<ProductoDTO> listaInactivos = service.findAllInactive();
            if (listaInactivos.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No hay registros Inactivos")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Lista de Productos Inactivos")
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

    @GetMapping("/producto/{id}")
    public ResponseEntity<?> getFindByIdProducto(@PathVariable Integer id) {
        try {
            ProductoEntity idProducto = service.findById(id);
            if (idProducto == null) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No se encontro el ID del producto")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);

            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Resultado de la busqueda")
                        .object(idProducto)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/producto/nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
        try {
            List<ProductoEntity> lista = service.buscarPorNombre(nombre);

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

    @GetMapping("/producto/marca/{marca}")
    public ResponseEntity<?> buscarPorMarca(@PathVariable String marca) {
        try {
            List<ProductoEntity> lista = service.buscarPorNombreMarca(marca);

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

    @GetMapping("/producto/categoria/{categoria}")
    public ResponseEntity<?> buscarPorNombreCategoria(@PathVariable String categoria) {
        try {
            List<ProductoEntity> lista = service.buscarPorNombreCategoria(categoria);

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

    @PostMapping("/producto")
    public ResponseEntity<?> saveProducto(@Valid @RequestBody ProductoEntity producto) {
        ProductoEntity saveProducto = null;
        try {
            saveProducto = service.save(producto);
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Guardado Exitosamente")
                    .object(saveProducto)
                    .build(), HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(e)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/producto/{id}")
    public ResponseEntity<?> updateProducto(@Valid @RequestBody ProductoEntity producto, @PathVariable Integer id) {
        ProductoEntity updateProducto = null;
        try {
            ProductoEntity productoId = service.findById(id);
            if (productoId != null) {
                updateProducto = service.save(producto);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Producto Actualizado")
                        .object(updateProducto)
                        .build(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No se encontro el Producto")
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

    @PatchMapping("/producto/{id_producto}")
    public ResponseEntity<?> changeOfStatus(@PathVariable Integer id_producto) {
        try {
            ProductoEntity productoEntity = service.findById(id_producto);
            if (productoEntity != null) {
                ProductoEntity productoChange = service.changeofState(productoEntity);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Estado actualizado")
                        .object(productoChange)
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

    @DeleteMapping("/producto/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Integer id) {
        try {
            ProductoEntity productoEntity = service.findById(id);
            if (productoEntity != null) {
                service.delete(productoEntity);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Estado actualizado")
                        .object(null)
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

     @GetMapping("/producto/reporte/pdf")
    public ResponseEntity<?> generarReportePdf() {
        try {

            List<ProductoDTO> productos = service.findAll();

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

            Stream.of("Codigo", "Nombre", "Stock", "Precio").forEach(columnTitle -> {
                PdfPCell header = new PdfPCell();
                header.setBorderWidth(2);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setPhrase(new Phrase(columnTitle, FontFactory.getFont(FontFactory.TIMES_BOLD, 16)));
                table.addCell(header);
            });

            // Celdas de datos
            for (ProductoDTO proveedor : productos) {
                table.addCell(proveedor.getCodigo());
                table.addCell(proveedor.getNombre());
                table.addCell(Integer.toString(proveedor.getStock())); 
                table.addCell(Double.toString(proveedor.getPrecio()));
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

    @GetMapping("/producto/reporte/excel")
    public ResponseEntity<byte[]> generarReporteExcel() throws IOException {
        try {
            List<ProductoDTO> productos = service.findAll();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Productos");

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("logo.jpg");
            byte[] bytes = inputStream.readAllBytes();
            int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();

            CreationHelper helper = workbook.getCreationHelper();
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(11);
            anchor.setRow1(0);
            anchor.setCol2(14);
            anchor.setRow2(5);

            anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
            Picture picture = drawing.createPicture(anchor, pictureIdx);
            picture.resize(0.4);

            Font titleFont = workbook.createFont();
            titleFont.setFontHeightInPoints((short) 20);
            titleFont.setBold(true);
            titleFont.setColor(IndexedColors.WHITE.getIndex());

            CellStyle titleCellStyle = workbook.createCellStyle();
            titleCellStyle.setFont(titleFont);
            titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
            titleCellStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
            titleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

            Font headerFont = workbook.createFont();
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerCellStyle.setFont(headerFont);
            CellStyle dataCellStyle = workbook.createCellStyle();
            dataCellStyle.setAlignment(HorizontalAlignment.CENTER);

            sheet.setColumnWidth(7, 4000);
            sheet.setColumnWidth(8, 9000);
            sheet.setColumnWidth(9, 5000);
            sheet.setColumnWidth(10, 5000);

            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(7);
            titleCell.setCellValue("REPORTE DE PRODUCTOS");
            titleCell.setCellStyle(titleCellStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 10));

            Row headerRow = sheet.createRow(1);
            headerRow.createCell(7).setCellValue("Codigo");
            headerRow.createCell(8).setCellValue("Nombre");
            headerRow.createCell(9).setCellValue("Precio");
            headerRow.createCell(10).setCellValue("Stock");

            for (Cell headerCell : headerRow) {
                headerCell.setCellStyle(headerCellStyle);
            }

            int rowNum = 2;
            for (ProductoDTO producto : productos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(7).setCellValue(producto.getCodigo());
                row.createCell(8).setCellValue(producto.getNombre());
                row.createCell(9).setCellValue(producto.getPrecio());
                row.createCell(10).setCellValue(producto.getStock());

                for (Cell cell : row) {
                    cell.setCellStyle(dataCellStyle);
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(
                    MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("filename", "reporte_productos.xlsx");
            headers.setContentLength(outputStream.size());

            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

