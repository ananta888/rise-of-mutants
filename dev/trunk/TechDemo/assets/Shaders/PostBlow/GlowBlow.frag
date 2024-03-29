#if defined(NEED_TEXCOORD1) 
    varying vec2 texCoord1;
#else 
    varying vec2 texCoord;
#endif

#ifdef HAS_GLOWMAP
  uniform sampler2D m_GlowMap;
#endif

#ifdef HAS_GLOWCOLOR
  uniform vec4 m_GlowColor;
#endif


void main(){

    #ifdef HAS_GLOWMAP
        #if defined(NEED_TEXCOORD1) 
vec4 GlowTex = texture2D(m_GlowMap, texCoord1);
           gl_FragColor = GlowTex;
        #else 
vec4 GlowTex = texture2D(m_GlowMap, texCoord);
           gl_FragColor = GlowTex;
        #endif
    #else
        #ifdef HAS_GLOWCOLOR
            gl_FragColor =  m_GlowColor;
        #else
            gl_FragColor = vec4(0.0);
        #endif
    #endif


}