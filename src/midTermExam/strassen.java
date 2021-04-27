package midTermExam;

import java.util.Random;
import java.util.Scanner;

public class strassen {
	
	public int[][] addMatrix(int[][] a, int[][] b){
		
		int[][] addedMatrix = new int[a.length][a.length];
		
		for(int i=0; i<a.length; i++) {
			for(int j=0; j<a.length; j++) {
				addedMatrix[i][j] = a[i][j] + b[i][j];
			}
		}
		return addedMatrix;
	}
	
	public int[][] subMatrix(int[][] a, int[][] b){
		int [][] subedMatrix = new int[a.length][a.length];
		
		for(int i=0; i<a.length; i++) {
			for(int j=0; j<a.length; j++) {
				subedMatrix[i][j] = a[i][j] - b[i][j];
			}
		}
		return subedMatrix;
	}
	
	public int[][] multipleMatrix(int length, int[][] a, int[][] b){
		
		int[][] muledMatrix = new int[length][length];
		
		for(int i=0; i<length; i++) {  // ijk 방식 사용 캐시 효율을 올리기위해선 행 접근 kij 방식을 사용하면 좀 더 높일 수 있음
			for(int j=0; j<length; j++) {
				for(int k=0; k<length; k++) {
					muledMatrix[i][j] += a[i][k] * b[k][j];
				}
			}
		}
		return muledMatrix;
	}
	
	public int[][] divideMatrix(int point, int rowPoint, int columnPoint, int[][] matrix){
		
		int[][] dividedMatrix = new int[point][point];
		
		for(int i=0, x=rowPoint; i<point; i++, x++) {
			for(int j=0, y=columnPoint; j<point; j++, y++) {
				dividedMatrix[i][j] = matrix[x][y];
			}
		}
		return dividedMatrix;
	}
	
	public int[][] combineMatrix(int[][] c11, int[][] c12, int[][] c21, int[][] c22){
		
		int[][] combiedMatrix = new int [c11.length*2][c11.length*2];
		
		for(int i=0; i<c11.length; i++) {
			for(int j=0; j<c11.length; j++) {
				combiedMatrix[i][j] = c11[i][j];  // 좌상단 행렬
				combiedMatrix[i][j+c11.length] = c12[i][j];  // 우상단 행렬
				combiedMatrix[i+c11.length][j] = c21[i][j];  // 좌하단 행렬
				combiedMatrix[i+c11.length][j+c11.length] = c22[i][j];  // 우하단 행렬
			}
		}
		return combiedMatrix;
	}
	
	public int[][] makeMatrix(int number) {
		
		Random r = new Random();
		int[][] matrix = new int [number][number];  //정사각 행렬만 스트라센 알고리즘으로 계산가능
		
		for(int i=0; i<number; i++) {
			for(int j=0; j<number; j++) {
				matrix[i][j] = r.nextInt(20);
			}
		}
		return matrix;
	}
	
    public int[][] strassenMatrix(int[][] a, int[][] b) {
		
		int matrixLength = a.length;
		
		if(a.length == 2) {
			return multipleMatrix(matrixLength, a, b);
		}
		
		else {
			int dividePoint = matrixLength / 2;
			
			int[][] a11 = divideMatrix(dividePoint, 0, 0, a);
			int[][] a12 = divideMatrix(dividePoint, 0, dividePoint, a);
			int[][] a21 = divideMatrix(dividePoint, dividePoint, 0, a);
			int[][] a22 = divideMatrix(dividePoint, dividePoint, dividePoint, a);
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
			
			return combineMatrix(c11, c12, c21, c22);
		}
	}

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in); 
		strassen strassen = new strassen();
		
		int number = scanner.nextInt();  // 2의 거듭제곱 정방형 배열만 가능하기에 2의 거듭제곱 들의 행과 열만 입력받음
		int[][] a = strassen.makeMatrix(number);  //행렬 a
		int[][] b = strassen.makeMatrix(number);  //행렬 b
		
		strassen.strassenMatrix(a, b);
	}

}
