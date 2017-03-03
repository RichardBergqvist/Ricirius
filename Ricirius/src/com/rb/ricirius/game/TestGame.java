package com.rb.ricirius.game;

import com.rb.ricirius.engine.Game;
import com.rb.ricirius.engine.GameObject;
import com.rb.ricirius.engine.graphics.Attenuation;
import com.rb.ricirius.engine.graphics.Material;
import com.rb.ricirius.engine.graphics.Model;
import com.rb.ricirius.engine.graphics.ModelRenderer;
import com.rb.ricirius.engine.graphics.Texture;
import com.rb.ricirius.engine.graphics.Vertex;
import com.rb.ricirius.engine.components.light.*;
import com.rb.ricirius.engine.math.Vector2f;
import com.rb.ricirius.engine.math.Vector3f;

public class TestGame extends Game {

	public void init() {
		float fieldDepth = 10;
		float fieldWidth = 10;

		Vertex[] vertices = new Vertex[] { new Vertex(new Vector3f(-fieldWidth, 0, -fieldDepth), new Vector2f(0, 0)),
				new Vertex(new Vector3f(-fieldWidth, 0, fieldDepth * 3), new Vector2f(0, 1)),
				new Vertex(new Vector3f(fieldWidth * 3, 0, -fieldDepth), new Vector2f(1, 0)),
				new Vertex(new Vector3f(fieldWidth * 3, 0, fieldDepth * 3), new Vector2f(1, 1)) };

		int indices[] = { 0, 1, 2, 2, 1, 3 };

		Model model = new Model(vertices, indices, true);
		Material properties = new Material(new Texture("test.png"), new Vector3f(1, 1, 1), 1, 8);

		ModelRenderer modelRenderer = new ModelRenderer(model, properties);
		GameObject gameObject = new GameObject();
		gameObject.addComponent(modelRenderer);
		gameObject.getTransformer().setPos(0, -1, 5);
		
		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight = new DirectionalLight(new BaseLight(new Vector3f(0, 0, 1), 0.4F), new Vector3f(1, 1, 1));
		directionalLightObject.addComponent(directionalLight);
	
		GameObject pointLightObject = new GameObject();
		PointLight pointLight = new PointLight(new BaseLight(new Vector3f(0, 1, 0), 0.4F), new Attenuation(0, 0, 1), new Vector3f(5, 0, 5), 100);
		pointLightObject.addComponent(pointLight);
		
		getRootObject().addChild(gameObject);
		getRootObject().addChild(directionalLightObject);
		getRootObject().addChild(pointLightObject);
	}
}