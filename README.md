# Strassen Algorithm  <div style="text-align: right", font-size : 30px;> 201301608 이재권 </div>

스트라센 알고리즘은 행렬의 곱연산을 수행할 때 행렬의 크기가 커질수록 연산속도는 n^3 으로 기하급수적으로 증가하게 된다.
컴퓨터 구조적으로 곱연산보다 합연산이 더 효율적이기에 곱연산을 최소화 하고 합연산으로 변환 한 것이 스트라센 알고리즘이다.

먼저 구현에 앞서 한가지 아쉬운 점은 정사각 행렬(2의 거듭제곱 꼴)만 가능하다는 것이다.

물론, 거듭제곱의 형태가 아닌 행렬의 경우 빈 공간을 0으로 채운뒤 연산을 수행해도 된다.

## 코드 설명
---
-분할 정복법을 사용하여 구현, 7개의 메소드를 사용

* makeMatrix : 행렬 생성 및 초기화
* addMatrix : 행렬의 합 연산
* subMatrix : 행렬의 차 연산
* multipleMatrix : 행렬의 곱 연산
* divideMatrix : 행렬의 4분할
* combineMatrix : 4분할된 행렬 병합
* strassenMatrix : 스트라센 알고리즘 메소드
* main

divideMatrix() : matrix 행렬을 입력된 행 과 열 기준으로 분할
```java
public int[][] divideMatrix(int point, int rowPoint, int columnPoint, int[][] matrix){
		
		int[][] dividedMatrix = new int[point][point];
		
        //전체 행렬과 부분 행렬로 나누어 지기에 for문에 변수를 2개를 활용
        //row, column 은 전체 행렬의 행과 열의미
		for(int i=0, x=rowPoint; i<point; i++, x++) {
			for(int j=0, y=columnPoint; j<point; j++, y++) {
				dividedMatrix[i][j] = matrix[x][y];
			}
		}
		return dividedMatrix;
	}
```

strassenMatrix() : 분할정복과 재귀적 메소드 호출을 하여 2x2 행렬이 될 때 까지 연산수행
```java
public int[][] strassenMatrix(int[][] a, int[][] b) {
		
		int matrixLength = a.length;
		
		if(a.length == 2) {
			return multipleMatrix(matrixLength, a, b);
		} // 2x2 행렬의 경우 일반 곱 연산 수행
		
		else {
			int dividePoint = matrixLength / 2; // 4분할을 하기 위한 중간지점 이 변수는 divideMatrix 에서 point 값으로 전달
			
			int[][] a11 = divideMatrix(dividePoint, 0, 0, a); //좌상단 행렬a
			int[][] a12 = divideMatrix(dividePoint, 0, dividePoint, a); // 우상단 행렬a
			int[][] a21 = divideMatrix(dividePoint, dividePoint, 0, a); // 좌하단 행렬a
			int[][] a22 = divideMatrix(dividePoint, dividePoint, dividePoint, a); //우하단 행렬a
			int[][] b11 = divideMatrix(dividePoint, 0, 0, b);
			int[][] b12 = divideMatrix(dividePoint, 0, dividePoint, b);
			int[][] b21 = divideMatrix(dividePoint, dividePoint, 0, b);
			int[][] b22 = divideMatrix(dividePoint, dividePoint, dividePoint, b);
			
			int[][] m1 = strassenMatrix(addMatrix(a11, a22), addMatrix(b11, b22));
			int[][] m2 = strassenMatrix(addMatrix(a21, a22), b11);
			int[][] m3 = strassenMatrix(a11, subMatrix(b12, b22));
			int[][] m4 = strassenMatrix(a22, subMatrix(b21, b11));
			int[][] m5 = strassenMatrix(addMatrix(a11, a12), b22);
			int[][] m6 = strassenMatrix(subMatrix(a21, a11), addMatrix(b11, b12));
			int[][] m7 = strassenMatrix(subMatrix(a12, a22), addMatrix(b21, b22));
			
			int[][] c11 = addMatrix(subMatrix(addMatrix(m1, m4), m5), m7);  //c11 = m1+m4-m5+m7
			int[][] c12 = addMatrix(m3, m5);  //c12 = m3+m5
			int[][] c21 = addMatrix(m2, m4);  //c21 = m2+m4
			int[][] c22 = addMatrix(subMatrix(addMatrix(m1, m3), m2), m6);  //c22 = m1+m3-m2+m6
			
			return combineMatrix(c11, c12, c21, c22); // 4분할된 행렬의 병합
		}
	}
```

main() : 행렬의 크기를 입력받아 행렬 생성 및 스트라센 알고리즘 실행
```java
public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in); 
		strassen strassen = new strassen();
		
		int number = scanner.nextInt();  // 2의 거듭제곱 정방형 배열만 가능하기에 2의 거듭제곱 들의 행과 열만 입력받음
		int[][] a = strassen.makeMatrix(number);  //행렬 a
		int[][] b = strassen.makeMatrix(number);  //행렬 b
		
		strassen.strassenMatrix(a, b); //행렬의 곱 a x b 실행
	}
```
## 아쉬운 점
---
- [ ] 일반적인 행렬의 곱연산 보다는 빠르지만, 자바에서 행렬의 접근(배열의 접근) 방식에 따라 연산 속도의 차이가 있다.
ex) 배열을 접근 할 때 행 기준으로 접근하게 된다면, 캐시 효율이 증가됨

- [ ] 정사각 행렬만 사용가능

## 소스 코드 링크
---
<https://github.com/ljk1256/strassenAlgorithm>