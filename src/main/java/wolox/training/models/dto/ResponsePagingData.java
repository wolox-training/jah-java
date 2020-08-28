package wolox.training.models.dto;

public class ResponsePagingData<T> {

    protected int count;
    protected int limit;
    protected int offset;
    protected int totalPages;
    protected long totalCount;
    protected int previousPage;
    protected int currentPage;
    protected int nextPage;
    protected T page;

    public ResponsePagingData(){

    }

    public ResponsePagingData(int count, int limit, int offset, int totalPages, long totalCount,
        int previousPage, int currentPage, int nextPage, T page){
        this.count = count;
        this.limit = limit;
        this.offset = offset;
        this.totalPages = totalPages;
        this.totalCount = totalCount;
        this.previousPage = previousPage;
        this.currentPage = currentPage;
        this.nextPage = nextPage;
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public T getPage() {
        return page;
    }

    public void setPage(T page) {
        this.page = page;
    }
}
