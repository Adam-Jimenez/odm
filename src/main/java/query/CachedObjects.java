package query;

/**
 * Everytime documents are fetched from the db
 * we keep a reference to both the object and the db object Id
 * in a map wrapped in this singleton
 * 
 * That way, we can update and delete object and their document-equivalent easily
 * 
 * @author adam
 *
 */
public class CachedObjects {

}
