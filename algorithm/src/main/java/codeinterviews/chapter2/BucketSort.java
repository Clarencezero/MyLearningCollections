package codeinterviews.chapter2;

/**
 * 桶排序: 核心思想是将要排序的数据分到几个有序的桶里,每个桶里的数据再单独进行排序。桶内排序完成之后,再把每个桶里的数据按照顺序依次取出,组成的序列就是有序的状态。
 * 桶排序的思想近乎彻底的分治思想。
 * 桶排序对要排序的数据要要求是非常苛刻的。
 *  1. 要排序的数据需要很容易就能划分成m个桶,并且,桶与桶之间有着一世枭雄的大小顺序。这样,每个桶内的数据都排序之后,桶与桶之间的数据不再进行排序。
 *  2. 数据在各个桶之间的分布是比较均匀的。
 *  3. 桶排序比较适合用在外部排序中。所谓外部排序就是数据存储在外部磁盘中,数据量比较大,内存有限,无法将数据全部加载到内存中
 *  比如10GB订单数据,希望按订单金额进行排序。
 *      1. 扫描一遍文件, 看订单金额所处的数据范围。如订单最小是1元,最大是10万元。将所有订单划分到100个桶里面,第一个桶存储1~100,第二个桶101~200...。
 *          每一个桶对应一个文件,并且按照金额范围的大小顺序编号命名(00,01,02...99)
 *      2. 对于不均匀的数据,我们可以继续对其进行划分,直到所有文件都能读入内存为止。
 *
 *  时间复杂度: O(n)
 *
 *
 */
public class BucketSort {

}
