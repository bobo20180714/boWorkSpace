package compiler;

public interface InterfaceCompiler {	//�ű��������ӿ�
	//�������ö������
	public static final int ENUM_ERROR_COMMON= 0;
	public static final int ENUM_ERROR_SCAN = 1;
	public static final int ENUM_ERROR_PARSE = 2;
	public static final int ENUM_ERROR_ANALYSE = 3;
	public static final int ENUM_ERROR_CHECK = 4; // ��Ϣ������
	public static final int ENUM_ERROR_GENERATE = 5; // �������ɴ���

	public CompilerOutput ScriptAnalyse(CompilerInput input);		//��������������뼰Ȩ�޼�飩
}

