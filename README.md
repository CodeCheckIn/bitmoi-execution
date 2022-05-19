# bitmoi-execution

# Diagram
https://docs.google.com/presentation/d/1tZ7lNzXC2uQhn9CE6zFiPNdAbP8x40kPPBw7e2GqyyM/edit#slide=id.g12c996c8542_4_0

https://docs.google.com/presentation/d/1tZ7lNzXC2uQhn9CE6zFiPNdAbP8x40kPPBw7e2GqyyM/edit#slide=id.g12c996c8542_4_51

# To do List
## Domain - Order kafka Consumer
1. 오더 서버 카프카 수신.
2. 오더 정보와 현재 코인 가격을 비교하는 로직.
3. 오더 상태 업데이트.
4. 체결 정보 저장.
5. 오더를 넣은 유저 지갑 가져오기.
6. 지갑 업데이트. 

# Domain - Batch Kafka Consumer
1. 배치 서버 카프카 수신.
2. 가격이 변동된 코인정보 수신.
3. 오더 테이블에 변동된 코인 아이디와 미체결 조건으로 가져오기.
4. 가져온 오더 레코드 업데이트.
5. 체결 인서트.
6. 오더에 있는 유저정보 가져오기.
7. 가져온 유저 정보 업데이트.
