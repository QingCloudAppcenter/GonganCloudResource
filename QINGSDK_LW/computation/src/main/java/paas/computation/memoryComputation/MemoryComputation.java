package paas.computation.memoryComputation;

import paas.common.response.Response;

public interface MemoryComputation {

    public AcionEntryResponse acionEntry(Object distributedDataset, String function, String parameterList, String accessToken);
    public TransformatEntryResponse transformatEntry(Object distributedDataset, String function, String parameterList, String accessToken);
    public CollectResponse collect(Object distributedDataset, String accessToken);
    public CollectResponse count(Object distributedDataset, String accessToken);
    public TakeResponse take(Object distributedDataset, int amount, String accessToken);
    public Response saveFile(Object distributedDataset, String fileType, String filePath, String accessToken);
    public MapResponse map(Object distributedDataset, String function, String accessToken);
    public FilterResponse filter(Object distributedDataset, String userDefinedFunction, String accessToken);
    public SampleResponse sample(Object distributedDataset, boolean replace, Float percentage, int randomSeed, String accessToken);
    public UnionResponse  union(Object distributedDataset1, Object distributedDataset2, String accessToken);
    public IntersectionResponse intersection(Object distributedDataset1, Object distributedDataset2, String accessToken);
    public DistinctResponse distinct(Object distributedDataset, String accessToken);
    public GroupByKeyResponse groupByKey(Object distributedDataset, String accessToken);
    public ReduceByKeyResponse reduceByKey(Object distributedDataset, String reduceFunction, String keyField, String accessToken);
    public SortByKeyResponse sortByKey(Object distributedDataset, boolean sort, String keyField, String accessToken);
    public JoinResponse join(Object distributedDataset1, Object distributedDataset2, int joinMethod, String accessToken);
    public Partitionsponse partition(Object distributedDataset, int partitionNumber, String accessToken);

}
