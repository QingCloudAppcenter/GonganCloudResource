package paas.computation.memoryComputation;

import paas.common.response.Response;

public class MemoryComputationImpl implements MemoryComputation {

    @Override
    public AcionEntryResponse acionEntry(Object distributedDataset, String function, String parameterList, String accessToken) {
        return null;
    }

    @Override
    public TransformatEntryResponse transformatEntry(Object distributedDataset, String function, String parameterList, String accessToken) {
        return null;
    }

    @Override
    public CollectResponse collect(Object distributedDataset, String accessToken) {
        return null;
    }

    @Override
    public CollectResponse count(Object distributedDataset, String accessToken) {
        return null;
    }

    @Override
    public TakeResponse take(Object distributedDataset, int amount, String accessToken) {
        return null;
    }

    @Override
    public Response saveFile(Object distributedDataset, String fileType, String filePath, String accessToken) {
        return null;
    }

    @Override
    public MapResponse map(Object distributedDataset, String function, String accessToken) {
        return null;
    }

    @Override
    public FilterResponse filter(Object distributedDataset, String userDefinedFunction, String accessToken) {
        return null;
    }

    @Override
    public SampleResponse sample(Object distributedDataset, boolean replace, Float percentage, int randomSeed, String accessToken) {
        return null;
    }

    @Override
    public UnionResponse union(Object distributedDataset1, Object distributedDataset2, String accessToken) {
        return null;
    }

    @Override
    public IntersectionResponse intersection(Object distributedDataset1, Object distributedDataset2, String accessToken) {
        return null;
    }

    @Override
    public DistinctResponse distinct(Object distributedDataset, String accessToken) {
        return null;
    }

    @Override
    public GroupByKeyResponse groupByKey(Object distributedDataset, String accessToken) {
        return null;
    }

    @Override
    public ReduceByKeyResponse reduceByKey(Object distributedDataset, String reduceFunction, String keyField, String accessToken) {
        return null;
    }

    @Override
    public SortByKeyResponse sortByKey(Object distributedDataset, boolean sort, String keyField, String accessToken) {
        return null;
    }

    @Override
    public JoinResponse join(Object distributedDataset1, Object distributedDataset2, int joinMethod, String accessToken) {
        return null;
    }

    @Override
    public Partitionsponse partition(Object distributedDataset, int partitionNumber, String accessToken) {
        return null;
    }
}
