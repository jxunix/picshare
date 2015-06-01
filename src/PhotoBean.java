package photoshare;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.commons.io.output.ByteArrayOutputStream;

/*
 * A simple PhotoBean class.
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class PhotoBean {
  private int id;
  private String caption = "";
  private byte[] data;
	private int albumId;
  private long size;
  private String contentType;
  private byte[] thumbdata;
  private static final int THUMBNAIL_WIDTH = 80;

  public int getId() {
    return id;
  }

  public String getCaption() {
    return caption;
  }

  public byte[] getData() {
    return data;
  }

	public int getAlbumId() {
		return albumId;
	}

  public long getSize() {
    return size;
  }

  public String getContentType() {
    return contentType;
  }

  public byte[] getThumbdata() {
    return thumbdata;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

  public void setSize(long size) {
    this.size = size;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public void setThumbdata(byte[] thumbdata) {
    this.thumbdata = thumbdata;
  }

  public String getFormat() {
    if (getContentType() != null) {
      return getContentType().substring(getContentType().lastIndexOf('/') + 1).toLowerCase();
    } else {
      return "";
    }
  }

  public void createThumbnail() {
    if (getData() != null && getContentType() != null) {

      try {
        BufferedImage source = ImageIO.read(new ByteArrayInputStream(getData()));
        int width = THUMBNAIL_WIDTH;
        int height = THUMBNAIL_WIDTH;
        BufferedImage thumb = new BufferedImage(width, height, source.getType());
        thumb.createGraphics().drawImage(source, 0, 0, width, height, null);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(thumb, getFormat(), bos);
        setThumbdata(bos.toByteArray());
      } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    }
  }
}
