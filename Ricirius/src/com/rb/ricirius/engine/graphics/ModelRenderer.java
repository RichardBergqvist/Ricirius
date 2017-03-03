package com.rb.ricirius.engine.graphics;

import com.rb.ricirius.engine.components.GameComponent;
import com.rb.ricirius.engine.graphics.shader.Shader;

public class ModelRenderer extends GameComponent {

	private Model model;
	private Material material;
	
	public ModelRenderer(Model model, Material material) {
		this.model = model;
		this.material = material;
	}
	
	public void addShader(Transformer transformer, Shader shader) {
		shader.bind();
		shader.updateUniforms(transformer, material);
	}
	
	@Override
	public void render(Transformer transformer, Shader shader) {
		addShader(transformer, shader);
		model.render();
	}
}