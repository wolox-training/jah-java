package wolox.training.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
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

}
