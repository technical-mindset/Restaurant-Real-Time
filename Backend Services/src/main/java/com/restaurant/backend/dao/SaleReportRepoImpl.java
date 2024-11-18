package com.restaurant.backend.dao;

import com.restaurant.backend.model.SaleDealReport;
import com.restaurant.backend.model.SaleItemReport;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@Transactional(readOnly = true)
public class SaleReportRepoImpl extends AbstractPersistenceManager<SaleItemReport> implements SaleReportRepoCustom {

    /**  A custom SQL native query (Not an HQL)  */
    // For Items
    @Override
    public List<SaleItemReport> findItemsBetweenDates(String startDate, String endDate, int check) {
        String dateFormat =
                switch (check) {
            case 1 -> "TO_CHAR(io.created_at, 'YYYY-MM-DD')";
            case 2 -> "TO_CHAR(io.created_at, 'YYYY-MM')";
            case 3 -> "TO_CHAR(io.created_at, 'YYYY')";
            default -> throw new IllegalArgumentException("Invalid check parameter. Must be 1, 2, or 3.");
        }, where = "";

        // Construct the SQL query using the dateFormat
        String sql = "SELECT ROW_NUMBER() OVER (ORDER BY " + dateFormat + " DESC, io.item_id) AS unique_number, " +
                "io.item_id as id, i.\"name\" AS name, " +
                "COUNT(io.item_id) AS total_order, " +
                "i.price as price_each, " +
                "SUM(io.price) AS total_revenue, " +
                "SUM(io.quantity) AS total_sale, " +
                dateFormat + " AS sale_date " +
                "FROM item_order io " +
                "INNER JOIN item i " +
                "ON io.item_id = i.id ";

        if (startDate != null && endDate != null || (startDate.length() > 0 && endDate.length() > 0)) {
            where += "WHERE " + dateFormat + " BETWEEN :startDate AND :endDate ";
        }



               sql += where + "GROUP BY io.item_id, i.\"name\", " +
                "i.price, " + dateFormat + " " +
                "ORDER BY sale_date DESC, io.item_id";



        Query query = entityManager.createNativeQuery(sql, SaleItemReport.class);

        if (startDate != null && endDate != null || (startDate.length() > 0 && endDate.length() > 0)) {
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
        }

        return query.getResultList();
    }

    // For Deals
    @Override
    public List<SaleDealReport> findDealBetweenDates(String startDate, String endDate, int check) {
        String dateFormat =
                switch (check) {
            case 1 -> "TO_CHAR(do2.created_at, 'YYYY-MM-DD')";
            case 2 -> "TO_CHAR(do2.created_at, 'YYYY-MM')";
            case 3 -> "TO_CHAR(do2.created_at, 'YYYY')";
            default -> throw new IllegalArgumentException("Invalid check parameter. Must be 1, 2, or 3.");
        } , where = "";

        // Construct the SQL query using the dateFormat
        String sql = "SELECT ROW_NUMBER() OVER (ORDER BY " + dateFormat + " DESC, do2.deal_id) AS unique_number, " +
                "do2.deal_id AS id, d.name AS name, " +
                "COUNT(do2.deal_id) AS total_order, " +
                "d.discounted_price AS price_each, " +
                "SUM(do2.price) AS total_revenue, " +
                "SUM(do2.quantity) AS total_sale, " +
                dateFormat + " AS sale_date " +
                "FROM deal_order do2 " +
                "INNER JOIN deal d " +
                "ON do2.deal_id = d.id ";

        if (startDate != null && endDate != null && startDate.length() > 0 && endDate.length() > 0) {
            where += "WHERE " + dateFormat + " BETWEEN :startDate AND :endDate ";
        }

        sql += where + "GROUP BY do2.deal_id, d.name, " +
                "d.discounted_price, " + dateFormat + " " +
                "ORDER BY sale_date DESC, do2.deal_id";

        // Create the query
        Query query = entityManager.createNativeQuery(sql, SaleDealReport.class);

        if (startDate != null && endDate != null && startDate.length() > 0 && endDate.length() > 0) {
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
        }

        return query.getResultList();
    }

}
