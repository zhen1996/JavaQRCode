package com.google.zxing.web;

import com.google.common.net.MediaType;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.GenericMultipleBarcodeReader;
import com.google.zxing.multi.MultipleBarcodeReader;
import com.jhlabs.image.GaussianFilter;
import com.jhlabs.image.SharpenFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageRecognition {
    private static final Logger log = Logger.getLogger(DecodeServlet.class.getName());

    // No real reason to let people upload more than ~64MB
    private static final long MAX_IMAGE_SIZE = 1L << 26;
    // No real reason to deal with more than ~32 megapixels
    private static final int MAX_PIXELS = 1 << 25;
    private static final byte[] REMAINDER_BUFFER = new byte[1 << 16];
    private static final Map<DecodeHintType, Object> HINTS;
    private static final Map<DecodeHintType, Object> HINTS_PURE;

    static {
        HINTS = new EnumMap<>(DecodeHintType.class);
        HINTS.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        HINTS.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(BarcodeFormat.class));
        HINTS_PURE = new EnumMap<>(HINTS);
        HINTS_PURE.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
    }

    public Collection<Result> processImage(BufferedImage image, int ImageFilterCount) throws RuntimeException {

        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));
        Collection<Result> results = new ArrayList<>(1);

        try {

            Reader reader = new MultiFormatReader();
            ReaderException savedException = null;
            try {
                // Look for multiple barcodes
                MultipleBarcodeReader multiReader = new GenericMultipleBarcodeReader(reader);
                Result[] theResults = multiReader.decodeMultiple(bitmap, HINTS);
                if (theResults != null) {
                    results.addAll(Arrays.asList(theResults));
                }
            } catch (ReaderException re) {
                savedException = re;
            }

            if (results.isEmpty()) {
                try {
                    // Look for pure barcode
                    Result theResult = reader.decode(bitmap, HINTS_PURE);
                    if (theResult != null) {
                        results.add(theResult);
                    }
                } catch (ReaderException re) {
                    savedException = re;
                }
            }

            if (results.isEmpty()) {
                try {
                    // Look for normal barcode in photo
                    Result theResult = reader.decode(bitmap, HINTS);
                    if (theResult != null) {
                        results.add(theResult);
                    }
                } catch (ReaderException re) {
                    savedException = re;
                }
            }

            if (results.isEmpty()) {
                try {
                    // Try again with other binarizer
                    BinaryBitmap hybridBitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Result theResult = reader.decode(hybridBitmap, HINTS);
                    if (theResult != null) {
                        results.add(theResult);
                    }
                } catch (ReaderException re) {
                    savedException = re;
                }
            }

//            if (results.isEmpty()) {
//                try {
//                    throw savedException == null ? NotFoundException.getNotFoundInstance() : savedException;
//                } catch (FormatException | ChecksumException e) {
//                    log.info(e.toString());
//                    errorResponse(request, response, "format");
//                } catch (ReaderException e) { // Including NotFoundException
//                    log.info(e.toString());
//                    errorResponse(request, response, "notfound");
//                }
//                return;
//            }

        } catch (RuntimeException re) {
            // Call out unexpected errors in the log clearly
            log.log(Level.WARNING, "Unexpected exception from library", re);
            throw re;
        }

        if(results.size() > 0) {
            return results;
        } else {
            if(ImageFilterCount == 0) {
                ImageFilterCount++;
                BufferedImage imageFilter = blurAndSharpImage(image);
                return processImage(imageFilter, ImageFilterCount);
            } else {
                return results;
            }
        }
    }

    private static BufferedImage blurAndSharpImage(BufferedImage image) {
        GaussianFilter filter = new GaussianFilter(5);
        BufferedImage imageDest = filter.filter(image, null);
        SharpenFilter filterSharp = new SharpenFilter();
        imageDest = filterSharp.filter(imageDest, null);
        return imageDest;
    }
}
