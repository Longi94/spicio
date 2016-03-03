package com.tlongdev.spicio.network.converter;

import com.tlongdev.spicio.domain.model.Image;
import com.tlongdev.spicio.domain.model.Images;
import com.tlongdev.spicio.network.model.TraktImage;
import com.tlongdev.spicio.network.model.TraktImages;

/**
 * @author Long
 * @since 2016. 03. 02.
 */
public class TraktModelConverter {

    public static Image convertToImage(TraktImage traktImage) {
        if (traktImage == null) {
            return null;
        }

        Image image = new Image();

        image.setFull(traktImage.getFull());
        image.setMedium(traktImage.getMedium());
        image.setThumb(traktImage.getThumb());

        return image;
    }

    public static Images convertToImage(TraktImages traktImages) {
        if (traktImages == null) {
            return null;
        }

        Images images = new Images();

        images.setBanner(convertToImage(traktImages.getBanner()));
        images.setClearArt(convertToImage(traktImages.getClearart()));
        images.setFanArt(convertToImage(traktImages.getFanart()));
        images.setLogo(convertToImage(traktImages.getLogo()));
        images.setPoster(convertToImage(traktImages.getPoster()));
        images.setScreenshot(convertToImage(traktImages.getScreenshot()));
        images.setThumb(convertToImage(traktImages.getThumb()));

        return images;
    }
}
