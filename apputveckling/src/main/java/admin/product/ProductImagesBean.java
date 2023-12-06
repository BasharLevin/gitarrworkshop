package admin.product;

import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.Part;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Product images bean
 * This class is used to get product images from the database
 * This class is also used to add product images to the database
 * This class is also used to get one product image from the database
 */
@Named
@SessionScoped
public class ProductImagesBean implements Serializable {

    @Produces
    @PersistenceContext(unitName = "ProductImages")
    private EntityManager entityManager;
    List<ProductImages> productImages;
    List<ProductImages> oneImage; // for the product page
    public ProductImagesBean() {
    }

    /**
     * Get all product images from the database
     * @param productId the product id
     * @return a list of product images
     */
    public List<ProductImages> getProductImages(int productId) {
        productImages = entityManager.createQuery("select p from ProductImages p where p.PRODUCT_ID = :productId", ProductImages.class)
                .setParameter("productId", productId)
                .getResultList();
        return productImages;
    }

    public void setProductImages(List<ProductImages> productImages) {
        this.productImages = productImages;
    }

    /**
     * Get one product image from the database
     * @param productId the product id
     * @return a string of the image path
     */
    public String getOneImage(int productId) {
       oneImage = entityManager.createQuery("select p from ProductImages p where p.PRODUCT_ID = :productId", ProductImages.class)
                .setParameter("productId", productId)
                .getResultList();
        return oneImage.get(0).getImgPathString();
    }


    public void setOneImage(List<ProductImages> oneImage) {
        this.oneImage = oneImage;
    }


    public void addProductImages(int productId, Part imageFile) {
       String filename=imageFile.getSubmittedFileName();
       String imagePath=getImgPath();
       saveImage(filename,imageFile,imagePath,productId);
    }

    @Transactional
    private void saveImage(String filename, Part imageFile, String imagePath, int productId) {
        try {
            ProductImages productImages1 =new ProductImages();
            productImages1.setPRODUCT_ID(productId);
            productImages1.setImgPathString(filename);
            entityManager.persist(productImages1);

        }catch (Exception e){
            throw e;
        }
    }


    private String getImgPath() {
        String webapp = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        return webapp+"../../src/main/webapp/resources/img";
    }

    /**
     * Delete product images from the database
     * @param productId the product id
     */
    public void deleteProductImages(int productId) {
        entityManager.createQuery("delete from ProductImages p where p.PRODUCT_ID = :productId")
                .setParameter("productId", productId)
                .executeUpdate();
    }
    /**
     * Delete one product image from the database
     * @param productId the product id
     * @param imgPathString the image path string [example:image49.jpg]
     */
    public void deleteOneProductImage(int productId, String imgPathString) {
        entityManager.createQuery("delete from ProductImages p where p.PRODUCT_ID = :productId and p.imgPathString = :imgPathString")
                .setParameter("productId", productId)
                .setParameter("imgPathString", imgPathString)
                .executeUpdate();
    }
}
