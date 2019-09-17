package century.gsdk.storage;
public interface StorageAPI {
    void initialze(String config)throws StorageException;
    void commit() throws StorageException;
    void rollback() throws StorageException;
    void begin() throws StorageException;
    void finish() throws StorageException;
    void insert(String sql)  throws StorageException;
    StorageSelect select(String sql)  throws StorageException;
    void update(String sql)  throws StorageException;
    void delete(String sql)  throws StorageException;
}
