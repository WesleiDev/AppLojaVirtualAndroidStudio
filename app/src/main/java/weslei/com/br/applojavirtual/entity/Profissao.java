package weslei.com.br.applojavirtual.entity;

public class Profissao {
	
	private int codProfissao;
	
	private String descricao;

	private String subDescricao;

	private String urlImage;



	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public String getSubDescricao() {
		return subDescricao;
	}

	public void setSubDescricao(String subDescricao) {
		this.subDescricao = subDescricao;
	}



	public int getCodProfissao() {
		return codProfissao;
	}

	public void setCodProfissao(int codProfissao) {
		this.codProfissao = codProfissao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



}
