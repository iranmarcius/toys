package toys.beans;

import toys.fs.FileToys;

/**
 * Contém especificações de uma imagem.
 * @author Iran Marcius 
 */
public class ImageSpecs {
	
	/**
	 * Largura mínima.
	 */
	protected int minWidth;
	
	/**
	 * Altura mínima.
	 */
	protected int minHeight;
	
	/**
	 * Largura máxima.
	 */
	protected int maxWidth;
	
	/**
	 * Altura máxima.
	 */
	protected int maxHeight;
	
	/**
	 * Tamanho máximo da imagem em bytes.
	 */
	protected long maxBytes;
	
	/**
	 * Indica se o formato deverá ser levado em conta na validação.
	 */
	protected boolean validateFormat;
	
	/**
	 * Indica se a especificação aceita o formato JPEG.
	 */
	protected boolean acceptJpeg;
	
	/**
	 * Indica se a especificação aceita o formato GIF.
	 */
	protected boolean acceptGif;
	
	/**
	 * Construtor default.
	 */
	public ImageSpecs() {
		super();
	}
	
	/**
	 * Retorna se existe alguma especificação de tamanho.
	 * @return <code>boolean</code>
	 */
	public boolean hasSpecs() {
		return
			(getMinWidth() > -1) ||
			(getMaxWidth() > -1) ||
			(getMinHeight() > -1) ||
			(getMaxHeight() > -1) ||
			(getMaxBytes() > 0) ||
			isValidateFormat();
	}
	
	/**
	 * Retorna se um valor está dentro dos limites de largura especificados.
	 * @param width Valor da largura a ser verificado.
	 * @return <code>boolean</code>
	 */
	public boolean isInWidthRange(int width) {
		boolean inRange = true;
		if (getMinWidth() > -1) inRange = inRange && (width >= getMinWidth());
		if (getMaxWidth() > -1) inRange = inRange && (width <= getMaxWidth());
		return inRange;
	}
	
	/**
	 * Retorna se um valor está dentro dos limites de altura especificados.
	 * @param height Valor da altura a ser verificado.
	 * @return <code>boolean</code>
	 */
	public boolean isInHeightRange(int height) {
		boolean inRange = true;
		if (getMinHeight() > -1) inRange = inRange && (height >= getMinHeight());
		if (getMaxHeight() > -1) inRange = inRange && (height<= getMaxHeight());
		return inRange;
	}
	
	/**
	 * Retorna se o tamanho fornecido está dentro do limite estabelecido.
	 * @param size Tamanho em bytes
	 * @return <code>boolean</code>
	 */
	public boolean isInSizeRange(long size) {
		return getMaxBytes() > 0 ? size <= getMaxBytes() : true;
	}
	
	/**
	 * Verifica e valida o formato da imagem com base em seu conteúdo.
	 * @param bytes Bytes de dados da imagem
	 * @return <code>boolean</code>
	 */
	public boolean isValidFormat(byte[] bytes) {
		return true;
	}
	
	/**
	 * Retorna se o nome do arquivo é válido de acordo com os tipos permitidos.
	 * @param filename Nome do arquivo
	 * @return <code>boolean</code>
	 */
	public boolean isValidFileExt(String filename) {
		String ext = FileToys.extractExtension(filename).toLowerCase();
		return
			(ext.equals(".jpg") && isAcceptJpeg()) ||
			(ext.equals(".gif") && isAcceptGif());
	}
	
	/*
	 * Acessors
	 */
	 
	public int getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

	public int getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

	public long getMaxBytes() {
		return maxBytes;
	}

	public void setMaxBytes(long maxBytes) {
		this.maxBytes = maxBytes;
	}

	public boolean isAcceptGif() {
		return acceptGif;
	}

	public void setAcceptGif(boolean acceptGif) {
		this.acceptGif = acceptGif;
	}

	public boolean isAcceptJpeg() {
		return acceptJpeg;
	}

	public void setAcceptJpeg(boolean acceptJpeg) {
		this.acceptJpeg = acceptJpeg;
	}

	public boolean isValidateFormat() {
		return validateFormat;
	}

	public void setValidateFormat(boolean validateFormat) {
		this.validateFormat = validateFormat;
	}

}
