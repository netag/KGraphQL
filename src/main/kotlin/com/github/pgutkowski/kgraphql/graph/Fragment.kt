package com.github.pgutkowski.kgraphql.graph

/**
 * fragment key needs to start with "..."
 */
interface Fragment {

    val fragmentGraph: Graph

    class External (
            key : String,
            override val fragmentGraph: Graph,
            val typeCondition: String? = null
    ) : Fragment, GraphNode(key, null, fragmentGraph, null){
        init {
            if(!key.startsWith("...")){
                throw IllegalArgumentException("External fragment key has to start with '...'")
            }
        }
    }

    class Inline (
            override val fragmentGraph: Graph,
            typeCondition: String
    ) : Fragment, GraphNode("on $typeCondition", null, fragmentGraph, null)
}