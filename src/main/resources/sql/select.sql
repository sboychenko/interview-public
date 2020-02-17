select a.*, sum(t.AMOUNT) as sum_all from TRANSFERS t
    left outer join ACCOUNTS a on t.SOURCE_ID = a.ID
where t.TRANSFER_TIME >= '2019-01-01' and t.SOURCE_ID != t.TARGET_ID
group by t.SOURCE_ID
having sum(t.AMOUNT) > 1000