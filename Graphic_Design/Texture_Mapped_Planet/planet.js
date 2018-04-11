//console.clear();

var gl;
var program, vPosition, vNormal, vTexCoord;
var xang, yang, zang, sx, cm;

var meshArray = new Array();
var normArray = new Array();
var texArray = new Array();

// Light Source
var lightPosition = vec4(1.0, 1.0, 0.0, 0.0 );

// Light Source Color Components
var lightAmbient = vec4(0.35, 0.35, 0.35, 1.0 );
var lightDiffuse = vec4( 1.0, 1.0, 1.0, 1.0 );
var lightSpecular = vec4( 1.0, 1.0, 1.0, 1.0 );

// Material Properties
var materialAmbient = vec4( 1.0, 0.0, 0.0, 1.0 );  // k_a
var materialDiffuse = vec4( 1.0, 0.0, 0.0, 1.0 );  // k_d
var materialSpecular = vec4( 1.0, 0.0, 0.0, 1.0 ); // k_s
var materialShininess = 20;

var ambientProduct, diffuseProduct, specularProduct;

function config() {
    
    document.getElementById('rotx').onchange = function(event) { rotateX(); }
    document.getElementById('roty').onchange = function(event) { rotateY(); }
    document.getElementById('rotz').onchange = function(event) { rotateZ(); }
    document.getElementById('scale').onchange = function(event) { scale(); }
    
    var canvas = document.getElementById( "gl-canvas" );
    
    gl = WebGLUtils.setupWebGL( canvas );
    if ( !gl ) { alert( "WebGL isn't available" ); }
    
    gl.clearDepth(1.0);                
    gl.enable(gl.DEPTH_TEST);           
    gl.depthFunc(gl.LEQUAL); 

    //
    //  Configure WebGL
    //
    gl.viewport( 0, 0, canvas.width, canvas.height );
    
    //  Load shaders and initialize attribute buffers
    program = initShaders( gl, "vertex-shader", "fragment-shader" );
    gl.useProgram( program );
       
    vPosition = gl.getAttribLocation( program, "vPosition" );
    vNormal = gl.getAttribLocation( program, "vNormal" );
    vTexCoord = gl.getAttribLocation( program, "vTexCoord" );
    
    xang = gl.getUniformLocation( program, "xang" );
    yang = gl.getUniformLocation( program, "yang" );
    zang = gl.getUniformLocation( program, "zang" );
    
    sx = gl.getUniformLocation( program, "scale" );
    
    ambientProduct = mult(lightAmbient, materialAmbient);
    diffuseProduct = mult(lightDiffuse, materialDiffuse);
    specularProduct = mult(lightSpecular, materialSpecular);
    
    createMesh();
    configureTexture();
    bufferData();
    
    gl.uniform1f( sx, 0.5 );
    
    gl.uniform4fv( gl.getUniformLocation( program, "ambientProduct"), flatten(ambientProduct) );
    gl.uniform4fv( gl.getUniformLocation( program, "diffuseProduct"), flatten(diffuseProduct) );
    gl.uniform4fv( gl.getUniformLocation( program, "specularProduct"), flatten(specularProduct) );	
    gl.uniform4fv( gl.getUniformLocation( program, "lightPosition" ), flatten(lightPosition) );
    gl.uniform1f( gl.getUniformLocation( program, "shininess" ),materialShininess );
    
   	render();
    
}


function configureTexture() {

	var img = new Image();
    img.src = document.getElementById("textureImage").src;
    img.setAttribute('crossorigin', '');
  
    // ------------------------------------
    // TODO Step 1 

    texture = gl.createTexture();

    gl.bindTexture( gl.TEXTURE_2D, texture );
    gl.pixelStorei( gl.UNPACK_FLIP_Y_WEBGL, true );
    gl.texImage2D( gl.TEXTURE_2D, 0, gl.RGB, gl.RGB, gl.UNSIGNED_BYTE, img );
    gl.generateMipmap( gl.TEXTURE_2D );
    gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.NEAREST );
    gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.NEAREST );

    gl.uniform1i(gl.getUniformLocation(program, "texture"), 0);



}


function createMesh() {	

		var PI = Math.PI;
		var increment = (2 * PI) / 64;
		var twopi = 2 * PI;
		
		for (var phi = 0; phi < (twopi / 2); phi += increment)	{
    
			for (var theta = 0; theta < twopi; theta += increment)	{
      
				var phi1 = phi;				
                var theta1 = theta;
        
				var phi2 = phi + increment;	
                var theta2 = theta;
        
				var phi3 = phi + increment;	
                var theta3 = theta + increment;
				
				var p1x = Math.sin(phi1) * Math.cos(theta1);
				var p1y = Math.sin(phi1) * Math.sin(theta1);
				var p1z = Math.cos(phi1);
				
				var p2x = Math.sin(phi2) * Math.cos(theta2);
				var p2y = Math.sin(phi2) * Math.sin(theta2);
				var p2z = Math.cos(phi2);

				var p3x = Math.sin(phi3) * Math.cos(theta3);
				var p3y = Math.sin(phi3) * Math.sin(theta3);
				var p3z = Math.cos(phi3);
				
				meshArray.push( vec3( p1x, p1y, p1z ) );
				meshArray.push( vec3( p2x, p2y, p2z ) );
				meshArray.push( vec3( p3x, p3y, p3z ) );
				
				normArray.push( vec3( p1x, p1y, p1z ) );
				normArray.push( vec3( p1x, p1y, p1z ) );
				normArray.push( vec3( p1x, p1y, p1z ) );
				
                // ------------------------------------
                // TODO Step 2 

                texArray.push( vec2( (2*Math.pi) - (theta1/(2*Math.pi)), (phi1/(2*Math.pi)) ) );
                texArray.push( vec2( (2*Math.pi) - (theta2/(2*Math.pi)), (phi2/(2*Math.pi)) ) );
                texArray.push( vec2( (2*Math.pi) - (theta3/(2*Math.pi)), (phi3/(2*Math.pi)) ) );



                theta2 = theta + increment;
				phi3 = phi;				
				
				p1x = Math.sin(phi1) * Math.cos(theta1);
				p1y = Math.sin(phi1) * Math.sin(theta1);
				p1z = Math.cos(phi1);
				
				p2x = Math.sin(phi2) * Math.cos(theta2);
				p2y = Math.sin(phi2) * Math.sin(theta2);
				p2z = Math.cos(phi2);

				p3x = Math.sin(phi3) * Math.cos(theta3);
				p3y = Math.sin(phi3) * Math.sin(theta3);
				p3z = Math.cos(phi3);
				
				meshArray.push( vec3( p1x, p1y, p1z ) );
				meshArray.push( vec3( p2x, p2y, p2z ) );
				meshArray.push( vec3( p3x, p3y, p3z ) );
				
				normArray.push( vec3( p1x, p1y, p1z ) );
				normArray.push( vec3( p1x, p1y, p1z ) );
				normArray.push( vec3( p1x, p1y, p1z ) );
        
                // ------------------------------------
                // TODO Step 2

                texArray.push( vec2( (2*Math.pi) - (theta1/(2*Math.pi)), (phi1/(2*Math.pi)) ) );
                texArray.push( vec2( (2*Math.pi) - (theta2/(2*Math.pi)), (phi2/(2*Math.pi)) ) );
                texArray.push( vec2( (2*Math.pi) - (theta3/(2*Math.pi)), (phi3/(2*Math.pi)) ) );
                
			}
	 } 
}

function rotateX() {
          
    var select = document.getElementById('rotx');
    var ang_deg = select.options[select.selectedIndex].value; 
    
    var radian =  ang_deg * (Math.PI/180.0);
    
    console.log( 'rotate x = ' + radian );
    
    gl.uniform1f( xang, radian );
    
    render();
    
}


function rotateY() {
    
    var select = document.getElementById('roty');
    var ang_deg = select.options[select.selectedIndex].value; 
    
    var radian =  ang_deg * (Math.PI/180.0);
    
    console.log( 'rotate y = ' + radian );
    
    gl.uniform1f( yang, radian );
    
    render();
    
}

function rotateZ() {
    
    var select = document.getElementById('rotz');
    var ang_deg = select.options[select.selectedIndex].value; 
    
    var radian =  ang_deg * (Math.PI/180.0);
    
    console.log( 'rotate z = ' + radian );
    
    gl.uniform1f( zang, radian );
    
    render();
    
}

function scale() {
    
    var select = document.getElementById('scale');
    var s_val = select.options[select.selectedIndex].value;
    
    console.log( 'scale = ' + s_val );
    
    gl.uniform1f( sx, s_val );
    
    render();
    
}



function bufferData() {
    
    var nBuffer = gl.createBuffer();
    var vBuffer = gl.createBuffer();
    var tBuffer = gl.createBuffer();
    
    gl.bindBuffer( gl.ARRAY_BUFFER, nBuffer);
    gl.vertexAttribPointer( vNormal, 3, gl.FLOAT, false, 0, 0 );
    gl.enableVertexAttribArray( vNormal );
    gl.bufferData( gl.ARRAY_BUFFER, flatten(normArray), gl.STATIC_DRAW );
    
    gl.bindBuffer( gl.ARRAY_BUFFER, vBuffer );
    gl.vertexAttribPointer( vPosition, 3, gl.FLOAT, false, 0, 0 );
    gl.enableVertexAttribArray( vPosition );
    gl.bufferData( gl.ARRAY_BUFFER, flatten(meshArray), gl.STATIC_DRAW );

    // ------------------------------------
    // TODO Step 3 
    
    gl.bindBuffer( gl.ARRAY_BUFFER, tBuffer );
    gl.vertexAttribPointer( vTexCoord, 2, gl.FLOAT, false, 0, 0 );
    gl.enableVertexAttribArray( vTexCoord );
    gl.bufferData( gl.ARRAY_BUFFER, flatten(texArray), gl.STATIC_DRAW );

    
}

function render() {
    
    for ( var i=0; i<meshArray.length; i+=3) gl.drawArrays( gl.TRIANGLES, i, 3 );
    
}

