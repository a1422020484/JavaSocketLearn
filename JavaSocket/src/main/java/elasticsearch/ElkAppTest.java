package elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ElkAppTest {
	@Test
	public void locationQuery() throws UnknownHostException {
		// 获取settings
		// 配置es集群的名字
		Settings settings = Settings.builder().put("cluster.name", "myes").build();
		// 获取client客户端
		TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(new TransportAddress(InetAddress.getByName("node01"), 9300));
		/**
		 * 基于矩形范围的数据搜索 40.0519526142,116.4178513254 40.0385828363,116.4465266673
		 */
		// 构建查询提交
		SearchResponse searchResponse = client.prepareSearch("platform_foreign_website").setTypes("store").setQuery(QueryBuilders.geoBoundingBoxQuery("location") // 矩形
						.setCorners(40.0519526142, 116.4178513254, 40.0385828363, 116.4465266673)).get();
		SearchHits searchHits = searchResponse.getHits();
		SearchHit[] hits = searchHits.getHits();
		for (SearchHit hit : hits) {
			System.out.println(hit.getSourceAsString());
		}

		System.out.println("====================================");

		/**
		 * 找出坐落在多边形当中的坐标点 40.0519526142,116.4178513254 40.0385828363,116.4465266673
		 * 40.0503813013,116.4562592119
		 */
		List<GeoPoint> points = new ArrayList<>();
		points.add(new GeoPoint(40.0519526142, 116.4178513254));
		points.add(new GeoPoint(40.0503813013, 116.4562592119));
		points.add(new GeoPoint(40.0385828363, 116.4465266673));

		SearchResponse searchResponse1 = client.prepareSearch("platform_foreign_website").setTypes("store").setQuery(QueryBuilders.geoPolygonQuery("location", points)).get();// 多边形
		SearchHits searchHits1 = searchResponse1.getHits();
		SearchHit[] hits1 = searchHits1.getHits();
		for (SearchHit hit2 : hits1) {
			System.out.println(hit2.getSourceAsString());
		}

		System.out.println("======================================");

		/**
		 *          * 以当前的点为中心，搜索落在半径范围内200公里的经纬度坐标点
		 *          *40.0488115498,116.4320345091          
		 */
		SearchResponse searchResponse2 = client.prepareSearch("platform_foreign_website").setTypes("store").setQuery(QueryBuilders.geoDistanceQuery("location") // 半径
						.point(40.0488115498, 116.4320345091).distance(200, DistanceUnit.KILOMETERS)).get();
		SearchHits searchHits2 = searchResponse2.getHits();
		SearchHit[] hits2 = searchHits2.getHits();
		for (SearchHit hit3 : hits2) {
			System.out.println(hit3.getSourceAsString());
		}
		client.close();
	}
}
