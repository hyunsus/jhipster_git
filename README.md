# JHipster 설치 과정

## JHipster란?
JHipster = Java Hipster

- 자바 기반 생성(generate) 개발 플랫폼
- Spring 진영에서도 생성 도구를 활용한 개발이 없어 생산성에서 뒤쳐졌지만, JHipster에서 만들게 되어 Spring에서도 빠른 생산성을 확보
- 생성 도구는 Yeoman이라는 기존 웹 *보일러플레이트 프로젝트 생성 도구인데 JHipster에서 Yeoman기반으로 만들어 활용
- 현대 웹 어플리케이션과 마이크로 서비스 아키텍처 프레임워크를 생성할 수 있는 플랫폼이다.

*보일러플레이트(코드) - 변화없이 여러 군데에서 반복되는 코드

## Webpack
규모가 큰 웹 애플리케이션은 복잡한 자바스크립트와 대규모 의존성 트리를 가지고 있는데 이 복잡한 애플리케이션을 하나의 Javascript 파일로 관리할 수 없다.

- **모듈**
    - 프로그램을 구성하는 구성 요소의 일부
    - 일반적으로 관련된 데이터와 함수들이 묶여서 모듈을 형성하고 파일 단위로 나뉘어진다.
    - 모듈화 프로그래밍은 기능별로 파일을 나눠가며 프로그래밍을 하는 것으로 유지보수가 쉽다는 장점이 있다.
- **번들**
    - 소프트웨어 및 일부 하드웨어와 함께 작동하는 데 필요한 모든 것을 포함하는 Package
- **번들러**
    - 지정한 단위로 파일들을 하나로 만들어서 요청에 대한 응답 환경을 만들어준다.
    - 번들러를 사용하면 소스 코드를 모듈별로 작성할 수 있고 모듈간 또는 외부 라이브러리의 의존성도 쉽게 관리 가능하다.
- **웹팩**은 프로젝트의 구조를 분석하고 자바스크립트 모듈을 비롯한 관련 리소스들을 찾은 다음 이를 브라우저에서 이용할 수 있는 번들로 묶고 패킹하는 **모듈 번들러(Module bundler)**다.

## Yeoman이란?

- *Scaffolding 툴이다.
- 웹 어플리케이션을 제작하기 전에 필요한 디렉토리 구조 및 기본적인 파일을 생성해 주는 도구이다.
- yeoman 이라는 툴을 이용하여, SpringBoot Template을 자동으로 구성하고 생성해준다.

*Scaffolding - 일부 MVC 프레임워크에서 사용하는 의미이다. 개발자가 사용하고자 하는 모델을 정의하면 자동으로 관련된 보일러플레이트 코드가 만들어지는 기법

## JHipster 의 장점
- 마이크로서비스 어플리케이션을 쉽고 빠르게 만들 수 있다.
- Cloud-Native Application을 생성할 수 있다.
- 쉽고 빠르게 Docker나 Kubernetes를 사용할 수 있다.

## JHipster Sample Project
JHipster를 이용하기 위해서는

- Java
- Git
- Node.js
- Yeoman ($npm install -g yo)
의 툴킷들이 설치되어야 한다.

## jhipster 애플리케이션 생성
JHipster 설치 ($npm install -g generator-jhipster)

JHipster 실행 ($ jhipster) 

**Application Type 선택 (Which *type* of application would you like to create?)**

- Monolithic application: 모놀리식 어플리케이션으로, 단순 프로젝트에서 유용하다.
- Gateway application: 게이트웨이는 마이크로 서비스 아키텍처에서 Gateway를 이용하도록 한다. Netflix OOS를 기본으로 이용한다.
- Microservice application: 마이크로 서비스를 개발할 수 있도록 프로젝트를 생성한다.

**어플리케이션 이름(What is the base name of your application)**

- 기본은 sample로 되어 있으며 어플리케이션 이름을 작성하면 된다.

**WebFlux 적용 여부(Do you want to make it reactive with Spring WebFlux)**

- 웹 플럭스를 사용할지를 지정한다.
- 일반 웹 어플리케이션 - N , 리액티브 프로그래밍으로 서비스 하고 싶으면 Y

**자바 베이스 패키지 지정하기(What is your default Java package name)**

- 기본 패키지를 지정한다. (기본은 com.mycompany.myapp으로 되어 있다.)
- Java 응용 프로그램은 이것을 루트 패키지로 사용합니다.

**Security 타입 설정하기(Which *type* of authentication would you like to use)**

- JWT authenication: 대표적인 stateless형 인증이다. JWT는 단순한 구조이면서도, 페이로드에 필요한 정보를 이용할 수 있어서 인기가 많다.
- OAuth 2.0 / OIDC Authentication: OAuth 2.0은 산업 표준 인증 프로토콜이며, 클라이언트가 쉽게 인증을 수행하면서도, 웹 어플리케이션을 위한 정형화된 인증 메커니즘을 제공한다. OIDC 는 (Open ID Connect)로 OAuth 2.0을 베이스로 클라이언트에서 인증을 수행할 수 있는 규격을 제공한다.
- HTTP Session Authentication: HTTP Session을 기반으로 인증을 수행한다.

**Database 타입 지정하기(Which *type* of database would you like to use)**

- SQL(H2, PostgreSQL, MySQL, MariaDB, Oracle, MSSQL): 이와 같이 관계형 데이터베이스를 연동할 때 사용한다.
- MongoDB: Document Base NoSQL의 대표 데이터베이스인 MongoDB 연동을 지원한다.
- Cassandra: 확장성이 뛰어난 NoSQL인 카산드라 연동을 지원한다.
- Neo4j: 베타로 제공하며, Graph Database이다. 관계등을 표현할 때 매우 훌륭한 NoSQL이다.
- No database: 데이터베이스 제공이 없을 때 사용한다.

**프로덕션용 데이터베이스 지정하기(Which *production* database would you lilke to use)**

- 실제 프로덕션용 데이터 베이스를 선택한다.
- 선택하면 DataSource 연동을 위해 각 DB 커넥션 의존성 라이브러리가 추가된다.

**개발용 데이터베이스 선택하기(Which *development* database would you like to use)**

- 개발 환경에서는 보통 H2를 이용하거나, 개발 환경의 데이터베이스로 사용을 하게 된다.
- 공용 개발환경을  위해서라면 MariaDB를 선택한다.

**Cache 설정하기(Which cache do you want to use)**

- Ehcache: 대표적인 로컬 캐시로 사용이 쉽다.
- Caffeine: Ehcache와 마찬가지로 로컬 캐시용으로 사용한다.
- Hazelcast: 분산 캐시를 이용하며, 어플리케이션에서 캐시 데이터를 저장하고, 이를 여러개의 어플리케이션에서 분산 저장하는 캐시이다. 데이터를 안전하게 분산하면서도, 동기화를 함께 진행하여 로컬 캐시보다는 안정적으로 캐싱을 수행할 수 있다.
- Infinispan: 분산 캐시이며, 여러 노드들에 캐시를 저장하는 대표적인 캐시이다.
- Memcached: 분산 캐시이며, 키/값 쌍의 형태 데이터를 캐시하는데 적합하며, 성능이 매우 좋다. 위 설명처럼 하이버네이트 2차 캐시를 동작시키면, Mencached는 무시된다.
- Redis: 대표적인 분산 캐시로, 다양한 자료구조를 제공하는 다용도 캐시이다.
- No cache: 캐시를 사용하지 않는다.(일반적으로 잘 이용하지 않는다.)

**Hibernate 2차 캐시 사용 여부(Do you want to use Hibernate 2nd level cache)**

- 하이버네이트는 쿼리 성능 향상을 위해서 2차 캐시를 이용하여, 조회한 데이터를 캐싱한다.

**의존성 관리 도구 설정(Would you like to use Maven or Gradle for building the backend)**

- 대표적인 의존성 관리도구인 Maven, Gradle 중 선택할 수 있다.

**jHipster Registry 설정 여부(Do you want to use the JHipster Registry to configure, monitor and scale your application)**

- JHipster Registry는 Eureka를 기본으로 사용하며, 서비스를 등록하고, 이들의 모니터링을 수행하도록 해준다.
- Eureka Service Discovery, Spring Cloud Config, Monitoring Dashboard 등을 제공한다.

**기타 기술 선택하기(Which other technologies would you like to use)**

- JHipster는 기본적으로 제공되는 것 이외에도 기타 기술들을 추가할 수 있다.
- Elasticsearch 검색엔진
- 웹소켓/li>
- Kafak
- OpenAPI

**Front 기술 설정하기(Which *Framework* would you like to use for the client)**

- 프런트엔드 프레임워크 선택

**Admin UI 사용하기(Do you want to generate the admin UI)**

- 기본적으로 jHipster는 어드민 화면을 제공한다.

**Admin UI Theme 선택하기(Would you like to use a Bootswatch theme)**

- JHipster는 BootStrap 프레임워크를 이용한다.
- [https://bootswatch.com](https://bootswatch.com/) 에서 부트스트랩 테마를 제공하고 있으며, 화면에서 보고 선택하면 된다.

**국제화 선택하기(Would you like to enable internationalization support)**

- 국제화 지원 여부

**대표 언어 선택하기(Please choose the native language of the application)**

- Korea가 맨 위에 있다.

****테스트 프레임워크 선택하기(Besides JUnit and Jest, which testing frameworks would you like to use?)****

- Junit, Jest 이외에 사용할 테스트 프레임워크를 선택할 수 있다.
- • Cypress: javascript 테스트 프레임워크이다.
- • Gatling: 오픈소스 로드 테스트용 테스트 프레임워크이다.
- • Cucumber: BDD(Behavor Driven Develpment)를 수행하느 대표적인 테스트 프레임워크이다.

**jHipster Marketplace 에서 선택하기(Would you like to install other generators from the JHipster Marketplace)**

- 위와 같이 JHipster Marketplace 로 부터 다른 generator를 설치할 수 있다.

## 서버 실행하기
./mvnw → 메이븐 실행 방법
./gradlew → 그래들 실행 방법
