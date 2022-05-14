# bitmoi-execution

## ToDo List
# Order 정보 주문 카프카로 받아서 Order테이블 업데이트 및 Execute 테이블 인서트.
1. Coin 가격 정보 가져오기
2. 가져온 가격 정보 비교 후 Order 테이블 업데이트.
3. 업데이트 된 내용 Execute 테이블 인서트.
# 배치 정보 카프카로 수집. 조건 확인 후 Order테이블 업데이트 및 Execute 테이블 인서트.
1. Order 테이블에서 가격 정보 맞는 오더 상태값 체결로 변경.
2. Excute 테이블에 체결된 Order 기록
3. (+@ 체결된 테이블 정보 카프카로 웹에 전달.)
