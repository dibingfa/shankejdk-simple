/*
 * Copyright (c) 1997, 2016, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 *
 */

#include "precompiled.hpp"
#include "libadt/vectset.hpp"
#include "memory/allocation.inline.hpp"
#include "opto/cfgnode.hpp"
#include "opto/connode.hpp"
#include "opto/loopnode.hpp"
#include "opto/machnode.hpp"
#include "opto/matcher.hpp"
#include "opto/node.hpp"
#include "opto/opcodes.hpp"
#include "opto/regmask.hpp"
#include "opto/type.hpp"
#include "utilities/copy.hpp"

class RegMask;
// #include "phase.hpp"
class PhaseTransform;
class PhaseGVN;

// Arena we are currently building Nodes in
const uint Node::NotAMachineReg = 0xffff0000;

#ifndef PRODUCT
extern int nodes_created;
#endif



// This constant used to initialize _out may be any non-null value.
// The value NULL is reserved for the top node only.
#define NO_OUT_ARRAY ((Node**)-1)

// This funny expression handshakes with Node::operator new
// to pull Compile::current out of the new node's _out field,
// and then calls a subroutine which manages most field
// initializations.  The only one which is tricky is the
// _idx field, which is const, and so must be initialized
// by a return value, not an assignment.
//
// (Aren't you thankful that Java finals don't require so many tricks?)
#define IDX_INIT(req) this->Init((req), (Compile*) this->_out)
#ifdef __clang__
#pragma clang diagnostic push
#pragma GCC diagnostic ignored "-Wuninitialized"
#endif

// Out-of-line code from node constructors.
// Executed only when extra debug info. is being passed around.
static void init_node_notes(Compile* C, int idx, Node_Notes* nn) {
  C->set_node_notes_at(idx, nn);
}

// Shared initialization code.
inline int Node::Init(int req, Compile* C) {
  assert(Compile::current() == C, "must use operator new(Compile*)");
  int idx = C->next_unique();

  // Allocate memory for the necessary number of edges.
  if (req > 0) {
    // Allocate space for _in array to have double alignment.
    _in = (Node **) ((char *) (C->node_arena()->Amalloc_D(req * sizeof(void*))));
  }
  // If there are default notes floating around, capture them:
  Node_Notes* nn = C->default_node_notes();
  if (nn != NULL)  init_node_notes(C, idx, nn);

  // Note:  At this point, C is dead,
  // and we begin to initialize the new Node.

  _cnt = _max = req;
  _outcnt = _outmax = 0;
  _class_id = Class_Node;
  _flags = 0;
  _out = NO_OUT_ARRAY;
  return idx;
}

//------------------------------Node-------------------------------------------
// Create a Node, with a given number of required edges.
Node::Node(uint req)
  : _idx(IDX_INIT(req))
{
  assert( req < Compile::current()->max_node_limit() - NodeLimitFudgeFactor, "Input limit exceeded" );
  debug_only( verify_construction() );
  NOT_PRODUCT(nodes_created++);
  if (req == 0) {
    assert( _in == (Node**)this, "Must not pass arg count to 'new'" );
    _in = NULL;
  } else {
    assert( _in[req-1] == this, "Must pass arg count to 'new'" );
    Node** to = _in;
    for(uint i = 0; i < req; i++) {
      to[i] = NULL;
    }
  }
}

//------------------------------Node-------------------------------------------
Node::Node(Node *n0)
  : _idx(IDX_INIT(1))
{
  debug_only( verify_construction() );
  NOT_PRODUCT(nodes_created++);
  // Assert we allocated space for input array already
  assert( _in[0] == this, "Must pass arg count to 'new'" );
  assert( is_not_dead(n0), "can not use dead node");
  _in[0] = n0; if (n0 != NULL) n0->add_out((Node *)this);
}

//------------------------------Node-------------------------------------------
Node::Node(Node *n0, Node *n1)
  : _idx(IDX_INIT(2))
{
  debug_only( verify_construction() );
  NOT_PRODUCT(nodes_created++);
  // Assert we allocated space for input array already
  assert( _in[1] == this, "Must pass arg count to 'new'" );
  assert( is_not_dead(n0), "can not use dead node");
  assert( is_not_dead(n1), "can not use dead node");
  _in[0] = n0; if (n0 != NULL) n0->add_out((Node *)this);
  _in[1] = n1; if (n1 != NULL) n1->add_out((Node *)this);
}

//------------------------------Node-------------------------------------------
Node::Node(Node *n0, Node *n1, Node *n2)
  : _idx(IDX_INIT(3))
{
  debug_only( verify_construction() );
  NOT_PRODUCT(nodes_created++);
  // Assert we allocated space for input array already
  assert( _in[2] == this, "Must pass arg count to 'new'" );
  assert( is_not_dead(n0), "can not use dead node");
  assert( is_not_dead(n1), "can not use dead node");
  assert( is_not_dead(n2), "can not use dead node");
  _in[0] = n0; if (n0 != NULL) n0->add_out((Node *)this);
  _in[1] = n1; if (n1 != NULL) n1->add_out((Node *)this);
  _in[2] = n2; if (n2 != NULL) n2->add_out((Node *)this);
}

//------------------------------Node-------------------------------------------
Node::Node(Node *n0, Node *n1, Node *n2, Node *n3)
  : _idx(IDX_INIT(4))
{
  debug_only( verify_construction() );
  NOT_PRODUCT(nodes_created++);
  // Assert we allocated space for input array already
  assert( _in[3] == this, "Must pass arg count to 'new'" );
  assert( is_not_dead(n0), "can not use dead node");
  assert( is_not_dead(n1), "can not use dead node");
  assert( is_not_dead(n2), "can not use dead node");
  assert( is_not_dead(n3), "can not use dead node");
  _in[0] = n0; if (n0 != NULL) n0->add_out((Node *)this);
  _in[1] = n1; if (n1 != NULL) n1->add_out((Node *)this);
  _in[2] = n2; if (n2 != NULL) n2->add_out((Node *)this);
  _in[3] = n3; if (n3 != NULL) n3->add_out((Node *)this);
}

//------------------------------Node-------------------------------------------
Node::Node(Node *n0, Node *n1, Node *n2, Node *n3, Node *n4)
  : _idx(IDX_INIT(5))
{
  debug_only( verify_construction() );
  NOT_PRODUCT(nodes_created++);
  // Assert we allocated space for input array already
  assert( _in[4] == this, "Must pass arg count to 'new'" );
  assert( is_not_dead(n0), "can not use dead node");
  assert( is_not_dead(n1), "can not use dead node");
  assert( is_not_dead(n2), "can not use dead node");
  assert( is_not_dead(n3), "can not use dead node");
  assert( is_not_dead(n4), "can not use dead node");
  _in[0] = n0; if (n0 != NULL) n0->add_out((Node *)this);
  _in[1] = n1; if (n1 != NULL) n1->add_out((Node *)this);
  _in[2] = n2; if (n2 != NULL) n2->add_out((Node *)this);
  _in[3] = n3; if (n3 != NULL) n3->add_out((Node *)this);
  _in[4] = n4; if (n4 != NULL) n4->add_out((Node *)this);
}

//------------------------------Node-------------------------------------------
Node::Node(Node *n0, Node *n1, Node *n2, Node *n3,
                     Node *n4, Node *n5)
  : _idx(IDX_INIT(6))
{
  debug_only( verify_construction() );
  NOT_PRODUCT(nodes_created++);
  // Assert we allocated space for input array already
  assert( _in[5] == this, "Must pass arg count to 'new'" );
  assert( is_not_dead(n0), "can not use dead node");
  assert( is_not_dead(n1), "can not use dead node");
  assert( is_not_dead(n2), "can not use dead node");
  assert( is_not_dead(n3), "can not use dead node");
  assert( is_not_dead(n4), "can not use dead node");
  assert( is_not_dead(n5), "can not use dead node");
  _in[0] = n0; if (n0 != NULL) n0->add_out((Node *)this);
  _in[1] = n1; if (n1 != NULL) n1->add_out((Node *)this);
  _in[2] = n2; if (n2 != NULL) n2->add_out((Node *)this);
  _in[3] = n3; if (n3 != NULL) n3->add_out((Node *)this);
  _in[4] = n4; if (n4 != NULL) n4->add_out((Node *)this);
  _in[5] = n5; if (n5 != NULL) n5->add_out((Node *)this);
}

//------------------------------Node-------------------------------------------
Node::Node(Node *n0, Node *n1, Node *n2, Node *n3,
                     Node *n4, Node *n5, Node *n6)
  : _idx(IDX_INIT(7))
{
  debug_only( verify_construction() );
  NOT_PRODUCT(nodes_created++);
  // Assert we allocated space for input array already
  assert( _in[6] == this, "Must pass arg count to 'new'" );
  assert( is_not_dead(n0), "can not use dead node");
  assert( is_not_dead(n1), "can not use dead node");
  assert( is_not_dead(n2), "can not use dead node");
  assert( is_not_dead(n3), "can not use dead node");
  assert( is_not_dead(n4), "can not use dead node");
  assert( is_not_dead(n5), "can not use dead node");
  assert( is_not_dead(n6), "can not use dead node");
  _in[0] = n0; if (n0 != NULL) n0->add_out((Node *)this);
  _in[1] = n1; if (n1 != NULL) n1->add_out((Node *)this);
  _in[2] = n2; if (n2 != NULL) n2->add_out((Node *)this);
  _in[3] = n3; if (n3 != NULL) n3->add_out((Node *)this);
  _in[4] = n4; if (n4 != NULL) n4->add_out((Node *)this);
  _in[5] = n5; if (n5 != NULL) n5->add_out((Node *)this);
  _in[6] = n6; if (n6 != NULL) n6->add_out((Node *)this);
}

#ifdef __clang__
#pragma clang diagnostic pop
#endif


//------------------------------clone------------------------------------------
// Clone a Node.
Node *Node::clone() const {
  Compile* C = Compile::current();
  uint s = size_of();           // Size of inherited Node
  Node *n = (Node*)C->node_arena()->Amalloc_D(size_of() + _max*sizeof(Node*));
  Copy::conjoint_words_to_lower((HeapWord*)this, (HeapWord*)n, s);
  // Set the new input pointer array
  n->_in = (Node**)(((char*)n)+s);
  // Cannot share the old output pointer array, so kill it
  n->_out = NO_OUT_ARRAY;
  // And reset the counters to 0
  n->_outcnt = 0;
  n->_outmax = 0;
  // Unlock this guy, since he is not in any hash table.
  debug_only(n->_hash_lock = 0);
  // Walk the old node's input list to duplicate its edges
  uint i;
  for( i = 0; i < len(); i++ ) {
    Node *x = in(i);
    n->_in[i] = x;
    if (x != NULL) x->add_out(n);
  }
  if (is_macro())
    C->add_macro_node(n);
  if (is_expensive())
    C->add_expensive_node(n);
  // If the cloned node is a range check dependent CastII, add it to the list.
  CastIINode* cast = n->isa_CastII();
  if (cast != NULL && cast->has_range_check()) {
    C->add_range_check_cast(cast);
  }

  n->set_idx(C->next_unique()); // Get new unique index as well
  debug_only( n->verify_construction() );
  NOT_PRODUCT(nodes_created++);
  // Do not patch over the debug_idx of a clone, because it makes it
  // impossible to break on the clone's moment of creation.
  //debug_only( n->set_debug_idx( debug_idx() ) );

  C->copy_node_notes_to(n, (Node*) this);

  // MachNode clone
  uint nopnds;
  if (this->is_Mach() && (nopnds = this->as_Mach()->num_opnds()) > 0) {
    MachNode *mach  = n->as_Mach();
    MachNode *mthis = this->as_Mach();
    // Get address of _opnd_array.
    // It should be the same offset since it is the clone of this node.
    MachOper **from = mthis->_opnds;
    MachOper **to = (MachOper **)((size_t)(&mach->_opnds) +
                    pointer_delta((const void*)from,
                                  (const void*)(&mthis->_opnds), 1));
    mach->_opnds = to;
    for ( uint i = 0; i < nopnds; ++i ) {
      to[i] = from[i]->clone(C);
    }
  }
  // cloning CallNode may need to clone JVMState
  if (n->is_Call()) {
    n->as_Call()->clone_jvms(C);
  }
  if (n->is_SafePoint()) {
    n->as_SafePoint()->clone_replaced_nodes();
  }
  return n;                     // Return the clone
}

//---------------------------setup_is_top--------------------------------------
// Call this when changing the top node, to reassert the invariants
// required by Node::is_top.  See Compile::set_cached_top_node.
void Node::setup_is_top() {
  if (this == (Node*)Compile::current()->top()) {
    // This node has just become top.  Kill its out array.
    _outcnt = _outmax = 0;
    _out = NULL;                           // marker value for top
    assert(is_top(), "must be top");
  } else {
    if (_out == NULL)  _out = NO_OUT_ARRAY;
    assert(!is_top(), "must not be top");
  }
}


//------------------------------~Node------------------------------------------
// Fancy destructor; eagerly attempt to reclaim Node numberings and storage
extern int reclaim_idx ;
extern int reclaim_in  ;
extern int reclaim_node;
void Node::destruct() {
  // Eagerly reclaim unique Node numberings
  Compile* compile = Compile::current();
  if ((uint)_idx+1 == compile->unique()) {
    compile->set_unique(compile->unique()-1);
  }
  // Clear debug info:
  Node_Notes* nn = compile->node_notes_at(_idx);
  if (nn != NULL)  nn->clear();
  // Walk the input array, freeing the corresponding output edges
  _cnt = _max;  // forget req/prec distinction
  uint i;
  for( i = 0; i < _max; i++ ) {
    set_req(i, NULL);
    //assert(def->out(def->outcnt()-1) == (Node *)this,"bad def-use hacking in reclaim");
  }
  assert(outcnt() == 0, "deleting a node must not leave a dangling use");
  // See if the input array was allocated just prior to the object
  int edge_size = _max*sizeof(void*);
  int out_edge_size = _outmax*sizeof(void*);
  char *edge_end = ((char*)_in) + edge_size;
  char *out_array = (char*)(_out == NO_OUT_ARRAY? NULL: _out);
  char *out_edge_end = out_array + out_edge_size;
  int node_size = size_of();

  // Free the output edge array
  if (out_edge_size > 0) {
    compile->node_arena()->Afree(out_array, out_edge_size);
  }

  // Free the input edge array and the node itself
  if( edge_end == (char*)this ) {
    // It was; free the input array and object all in one hit
    compile->node_arena()->Afree(_in,edge_size+node_size);
  } else {

    // Free just the input array
    compile->node_arena()->Afree(_in,edge_size);

    // Free just the object
    compile->node_arena()->Afree(this,node_size);
  }
  if (is_macro()) {
    compile->remove_macro_node(this);
  }
  if (is_expensive()) {
    compile->remove_expensive_node(this);
  }
  CastIINode* cast = isa_CastII();
  if (cast != NULL && cast->has_range_check()) {
    compile->remove_range_check_cast(cast);
  }

  if (is_SafePoint()) {
    as_SafePoint()->delete_replaced_nodes();
  }
}

//------------------------------grow-------------------------------------------
// Grow the input array, making space for more edges
void Node::grow( uint len ) {
  Arena* arena = Compile::current()->node_arena();
  uint new_max = _max;
  if( new_max == 0 ) {
    _max = 4;
    _in = (Node**)arena->Amalloc(4*sizeof(Node*));
    Node** to = _in;
    to[0] = NULL;
    to[1] = NULL;
    to[2] = NULL;
    to[3] = NULL;
    return;
  }
  while( new_max <= len ) new_max <<= 1; // Find next power-of-2
  // Trimming to limit allows a uint8 to handle up to 255 edges.
  // Previously I was using only powers-of-2 which peaked at 128 edges.
  //if( new_max >= limit ) new_max = limit-1;
  _in = (Node**)arena->Arealloc(_in, _max*sizeof(Node*), new_max*sizeof(Node*));
  Copy::zero_to_bytes(&_in[_max], (new_max-_max)*sizeof(Node*)); // NULL all new space
  _max = new_max;               // Record new max length
  // This assertion makes sure that Node::_max is wide enough to
  // represent the numerical value of new_max.
  assert(_max == new_max && _max > len, "int width of _max is too small");
}

//-----------------------------out_grow----------------------------------------
// Grow the input array, making space for more edges
void Node::out_grow( uint len ) {
  assert(!is_top(), "cannot grow a top node's out array");
  Arena* arena = Compile::current()->node_arena();
  uint new_max = _outmax;
  if( new_max == 0 ) {
    _outmax = 4;
    _out = (Node **)arena->Amalloc(4*sizeof(Node*));
    return;
  }
  while( new_max <= len ) new_max <<= 1; // Find next power-of-2
  // Trimming to limit allows a uint8 to handle up to 255 edges.
  // Previously I was using only powers-of-2 which peaked at 128 edges.
  //if( new_max >= limit ) new_max = limit-1;
  assert(_out != NULL && _out != NO_OUT_ARRAY, "out must have sensible value");
  _out = (Node**)arena->Arealloc(_out,_outmax*sizeof(Node*),new_max*sizeof(Node*));
  //Copy::zero_to_bytes(&_out[_outmax], (new_max-_outmax)*sizeof(Node*)); // NULL all new space
  _outmax = new_max;               // Record new max length
  // This assertion makes sure that Node::_max is wide enough to
  // represent the numerical value of new_max.
  assert(_outmax == new_max && _outmax > len, "int width of _outmax is too small");
}



//------------------------------is_unreachable---------------------------------
bool Node::is_unreachable(PhaseIterGVN &igvn) const {
  assert(!is_Mach(), "doesn't work with MachNodes");
  return outcnt() == 0 || igvn.type(this) == Type::TOP || in(0)->is_top();
}

//------------------------------add_req----------------------------------------
// Add a new required input at the end
void Node::add_req( Node *n ) {
  assert( is_not_dead(n), "can not use dead node");

  // Look to see if I can move precedence down one without reallocating
  if( (_cnt >= _max) || (in(_max-1) != NULL) )
    grow( _max+1 );

  // Find a precedence edge to move
  if( in(_cnt) != NULL ) {       // Next precedence edge is busy?
    uint i;
    for( i=_cnt; i<_max; i++ )
      if( in(i) == NULL )       // Find the NULL at end of prec edge list
        break;                  // There must be one, since we grew the array
    _in[i] = in(_cnt);          // Move prec over, making space for req edge
  }
  _in[_cnt++] = n;            // Stuff over old prec edge
  if (n != NULL) n->add_out((Node *)this);
}

//---------------------------add_req_batch-------------------------------------
// Add a new required input at the end
void Node::add_req_batch( Node *n, uint m ) {
  assert( is_not_dead(n), "can not use dead node");
  // check various edge cases
  if ((int)m <= 1) {
    assert((int)m >= 0, "oob");
    if (m != 0)  add_req(n);
    return;
  }

  // Look to see if I can move precedence down one without reallocating
  if( (_cnt+m) > _max || _in[_max-m] )
    grow( _max+m );

  // Find a precedence edge to move
  if( _in[_cnt] != NULL ) {     // Next precedence edge is busy?
    uint i;
    for( i=_cnt; i<_max; i++ )
      if( _in[i] == NULL )      // Find the NULL at end of prec edge list
        break;                  // There must be one, since we grew the array
    // Slide all the precs over by m positions (assume #prec << m).
    Copy::conjoint_words_to_higher((HeapWord*)&_in[_cnt], (HeapWord*)&_in[_cnt+m], ((i-_cnt)*sizeof(Node*)));
  }

  // Stuff over the old prec edges
  for(uint i=0; i<m; i++ ) {
    _in[_cnt++] = n;
  }

  // Insert multiple out edges on the node.
  if (n != NULL && !n->is_top()) {
    for(uint i=0; i<m; i++ ) {
      n->add_out((Node *)this);
    }
  }
}

//------------------------------del_req----------------------------------------
// Delete the required edge and compact the edge array
void Node::del_req( uint idx ) {
  assert( idx < _cnt, "oob");
  assert( !VerifyHashTableKeys || _hash_lock == 0,
          "remove node from hash table before modifying it");
  // First remove corresponding def-use edge
  Node *n = in(idx);
  if (n != NULL) n->del_out((Node *)this);
  _in[idx] = in(--_cnt); // Compact the array
  // Avoid spec violation: Gap in prec edges.
  close_prec_gap_at(_cnt);
}

//------------------------------del_req_ordered--------------------------------
// Delete the required edge and compact the edge array with preserved order
void Node::del_req_ordered( uint idx ) {
  assert( idx < _cnt, "oob");
  assert( !VerifyHashTableKeys || _hash_lock == 0,
          "remove node from hash table before modifying it");
  // First remove corresponding def-use edge
  Node *n = in(idx);
  if (n != NULL) n->del_out((Node *)this);
  if (idx < --_cnt) {    // Not last edge ?
    Copy::conjoint_words_to_lower((HeapWord*)&_in[idx+1], (HeapWord*)&_in[idx], ((_cnt-idx)*sizeof(Node*)));
  }
  // Avoid spec violation: Gap in prec edges.
  close_prec_gap_at(_cnt);
}

//------------------------------ins_req----------------------------------------
// Insert a new required input at the end
void Node::ins_req( uint idx, Node *n ) {
  assert( is_not_dead(n), "can not use dead node");
  add_req(NULL);                // Make space
  assert( idx < _max, "Must have allocated enough space");
  // Slide over
  if(_cnt-idx-1 > 0) {
    Copy::conjoint_words_to_higher((HeapWord*)&_in[idx], (HeapWord*)&_in[idx+1], ((_cnt-idx-1)*sizeof(Node*)));
  }
  _in[idx] = n;                            // Stuff over old required edge
  if (n != NULL) n->add_out((Node *)this); // Add reciprocal def-use edge
}

//-----------------------------find_edge---------------------------------------
int Node::find_edge(Node* n) {
  for (uint i = 0; i < len(); i++) {
    if (_in[i] == n)  return i;
  }
  return -1;
}

//----------------------------replace_edge-------------------------------------
int Node::replace_edge(Node* old, Node* neww) {
  if (old == neww)  return 0;  // nothing to do
  uint nrep = 0;
  for (uint i = 0; i < len(); i++) {
    if (in(i) == old) {
      if (i < req()) {
        set_req(i, neww);
      } else {
        assert(find_prec_edge(neww) == -1, err_msg("spec violation: duplicated prec edge (node %d -> %d)", _idx, neww->_idx));
        set_prec(i, neww);
      }
      nrep++;
    }
  }
  return nrep;
}

/**
 * Replace input edges in the range pointing to 'old' node.
 */
int Node::replace_edges_in_range(Node* old, Node* neww, int start, int end) {
  if (old == neww)  return 0;  // nothing to do
  uint nrep = 0;
  for (int i = start; i < end; i++) {
    if (in(i) == old) {
      set_req(i, neww);
      nrep++;
    }
  }
  return nrep;
}

//-------------------------disconnect_inputs-----------------------------------
// NULL out all inputs to eliminate incoming Def-Use edges.
// Return the number of edges between 'n' and 'this'
int Node::disconnect_inputs(Node *n, Compile* C) {
  int edges_to_n = 0;

  uint cnt = req();
  for( uint i = 0; i < cnt; ++i ) {
    if( in(i) == 0 ) continue;
    if( in(i) == n ) ++edges_to_n;
    set_req(i, NULL);
  }
  // Remove precedence edges if any exist
  // Note: Safepoints may have precedence edges, even during parsing
  if( (req() != len()) && (in(req()) != NULL) ) {
    uint max = len();
    for( uint i = 0; i < max; ++i ) {
      if( in(i) == 0 ) continue;
      if( in(i) == n ) ++edges_to_n;
      set_prec(i, NULL);
    }
  }

  // Node::destruct requires all out edges be deleted first
  // debug_only(destruct();)   // no reuse benefit expected
  if (edges_to_n == 0) {
    C->record_dead_node(_idx);
  }
  return edges_to_n;
}

//-----------------------------uncast---------------------------------------
// %%% Temporary, until we sort out CheckCastPP vs. CastPP.
// Strip away casting.  (It is depth-limited.)
Node* Node::uncast() const {
  // Should be inline:
  //return is_ConstraintCast() ? uncast_helper(this) : (Node*) this;
  if (is_ConstraintCast() || is_CheckCastPP())
    return uncast_helper(this);
  else
    return (Node*) this;
}

// Find out of current node that matches opcode.
Node* Node::find_out_with(int opcode) {
  for (DUIterator_Fast imax, i = fast_outs(imax); i < imax; i++) {
    Node* use = fast_out(i);
    if (use->Opcode() == opcode) {
      return use;
    }
  }
  return NULL;
}

//---------------------------uncast_helper-------------------------------------
Node* Node::uncast_helper(const Node* p) {

  while (true) {
    if (p == NULL || p->req() != 2) {
      break;
    } else if (p->is_ConstraintCast()) {
      p = p->in(1);
    } else if (p->is_CheckCastPP()) {
      p = p->in(1);
    } else {
      break;
    }
  }
  return (Node*) p;
}

//------------------------------add_prec---------------------------------------
// Add a new precedence input.  Precedence inputs are unordered, with
// duplicates removed and NULLs packed down at the end.
void Node::add_prec( Node *n ) {
  assert( is_not_dead(n), "can not use dead node");

  // Check for NULL at end
  if( _cnt >= _max || in(_max-1) )
    grow( _max+1 );

  // Find a precedence edge to move
  uint i = _cnt;
  while( in(i) != NULL ) {
    if (in(i) == n) return; // Avoid spec violation: duplicated prec edge.
    i++;
  }
  _in[i] = n;                                // Stuff prec edge over NULL
  if ( n != NULL) n->add_out((Node *)this);  // Add mirror edge

}

//------------------------------rm_prec----------------------------------------
// Remove a precedence input.  Precedence inputs are unordered, with
// duplicates removed and NULLs packed down at the end.
void Node::rm_prec( uint j ) {
  assert(j < _max, err_msg("oob: i=%d, _max=%d", j, _max));
  assert(j >= _cnt, "not a precedence edge");
  if (_in[j] == NULL) return;   // Avoid spec violation: Gap in prec edges.
  _in[j]->del_out((Node *)this);
  close_prec_gap_at(j);
}

//------------------------------size_of----------------------------------------
uint Node::size_of() const { return sizeof(*this); }

//------------------------------ideal_reg--------------------------------------
uint Node::ideal_reg() const { return 0; }

//------------------------------jvms-------------------------------------------
JVMState* Node::jvms() const { return NULL; }


//------------------------------format-----------------------------------------
// Print as assembly
void Node::format( PhaseRegAlloc *, outputStream *st ) const {}
//------------------------------emit-------------------------------------------
// Emit bytes starting at parameter 'ptr'.
void Node::emit(CodeBuffer &cbuf, PhaseRegAlloc *ra_) const {}
//------------------------------size-------------------------------------------
// Size of instruction in bytes
uint Node::size(PhaseRegAlloc *ra_) const { return 0; }

//------------------------------CFG Construction-------------------------------
// Nodes that end basic blocks, e.g. IfTrue/IfFalse, JumpProjNode, Root,
// Goto and Return.
const Node *Node::is_block_proj() const { return 0; }

// Minimum guaranteed type
const Type *Node::bottom_type() const { return Type::BOTTOM; }


//------------------------------raise_bottom_type------------------------------
// Get the worst-case Type output for this Node.
void Node::raise_bottom_type(const Type* new_type) {
  if (is_Type()) {
    TypeNode *n = this->as_Type();
    if (VerifyAliases) {
      assert(new_type->higher_equal_speculative(n->type()), "new type must refine old type");
    }
    n->set_type(new_type);
  } else if (is_Load()) {
    LoadNode *n = this->as_Load();
    if (VerifyAliases) {
      assert(new_type->higher_equal_speculative(n->type()), "new type must refine old type");
    }
    n->set_type(new_type);
  }
}

//------------------------------Identity---------------------------------------
// Return a node that the given node is equivalent to.
Node *Node::Identity( PhaseTransform * ) {
  return this;                  // Default to no identities
}

//------------------------------Value------------------------------------------
// Compute a new Type for a node using the Type of the inputs.
const Type *Node::Value( PhaseTransform * ) const {
  return bottom_type();         // Default to worst-case Type
}

//------------------------------Ideal------------------------------------------
//
// 'Idealize' the graph rooted at this Node.
//
// In order to be efficient and flexible there are some subtle invariants
// these Ideal calls need to hold.  Running with '+VerifyIterativeGVN' checks
// these invariants, although its too slow to have on by default.  If you are
// hacking an Ideal call, be sure to test with +VerifyIterativeGVN!
//
// The Ideal call almost arbitrarily reshape the graph rooted at the 'this'
// pointer.  If ANY change is made, it must return the root of the reshaped
// graph - even if the root is the same Node.  Example: swapping the inputs
// to an AddINode gives the same answer and same root, but you still have to
// return the 'this' pointer instead of NULL.
//
// You cannot return an OLD Node, except for the 'this' pointer.  Use the
// Identity call to return an old Node; basically if Identity can find
// another Node have the Ideal call make no change and return NULL.
// Example: AddINode::Ideal must check for add of zero; in this case it
// returns NULL instead of doing any graph reshaping.
//
// You cannot modify any old Nodes except for the 'this' pointer.  Due to
// sharing there may be other users of the old Nodes relying on their current
// semantics.  Modifying them will break the other users.
// Example: when reshape "(X+3)+4" into "X+7" you must leave the Node for
// "X+3" unchanged in case it is shared.
//
// If you modify the 'this' pointer's inputs, you should use
// 'set_req'.  If you are making a new Node (either as the new root or
// some new internal piece) you may use 'init_req' to set the initial
// value.  You can make a new Node with either 'new' or 'clone'.  In
// either case, def-use info is correctly maintained.
//
// Example: reshape "(X+3)+4" into "X+7":
//    set_req(1, in(1)->in(1));
//    set_req(2, phase->intcon(7));
//    return this;
// Example: reshape "X*4" into "X<<2"
//    return new (C) LShiftINode(in(1), phase->intcon(2));
//
// You must call 'phase->transform(X)' on any new Nodes X you make, except
// for the returned root node.  Example: reshape "X*31" with "(X<<5)-X".
//    Node *shift=phase->transform(new(C)LShiftINode(in(1),phase->intcon(5)));
//    return new (C) AddINode(shift, in(1));
//
// When making a Node for a constant use 'phase->makecon' or 'phase->intcon'.
// These forms are faster than 'phase->transform(new (C) ConNode())' and Do
// The Right Thing with def-use info.
//
// You cannot bury the 'this' Node inside of a graph reshape.  If the reshaped
// graph uses the 'this' Node it must be the root.  If you want a Node with
// the same Opcode as the 'this' pointer use 'clone'.
//
Node *Node::Ideal(PhaseGVN *phase, bool can_reshape) {
  return NULL;                  // Default to being Ideal already
}

// Some nodes have specific Ideal subgraph transformations only if they are
// unique users of specific nodes. Such nodes should be put on IGVN worklist
// for the transformations to happen.
bool Node::has_special_unique_user() const {
  assert(outcnt() == 1, "match only for unique out");
  Node* n = unique_out();
  int op  = Opcode();
  if( this->is_Store() ) {
    // Condition for back-to-back stores folding.
    return n->Opcode() == op && n->in(MemNode::Memory) == this;
  } else if (this->is_Load() || this->is_DecodeN()) {
    // Condition for removing an unused LoadNode or DecodeNNode from the MemBarAcquire precedence input
    return n->Opcode() == Op_MemBarAcquire;
  } else if( op == Op_AddL ) {
    // Condition for convL2I(addL(x,y)) ==> addI(convL2I(x),convL2I(y))
    return n->Opcode() == Op_ConvL2I && n->in(1) == this;
  } else if( op == Op_SubI || op == Op_SubL ) {
    // Condition for subI(x,subI(y,z)) ==> subI(addI(x,z),y)
    return n->Opcode() == op && n->in(2) == this;
  }
  return false;
};

//--------------------------find_exact_control---------------------------------
// Skip Proj and CatchProj nodes chains. Check for Null and Top.
Node* Node::find_exact_control(Node* ctrl) {
  if (ctrl == NULL && this->is_Region())
    ctrl = this->as_Region()->is_copy();

  if (ctrl != NULL && ctrl->is_CatchProj()) {
    if (ctrl->as_CatchProj()->_con == CatchProjNode::fall_through_index)
      ctrl = ctrl->in(0);
    if (ctrl != NULL && !ctrl->is_top())
      ctrl = ctrl->in(0);
  }

  if (ctrl != NULL && ctrl->is_Proj())
    ctrl = ctrl->in(0);

  return ctrl;
}

//--------------------------dominates------------------------------------------
// Helper function for MemNode::all_controls_dominate().
// Check if 'this' control node dominates or equal to 'sub' control node.
// We already know that if any path back to Root or Start reaches 'this',
// then all paths so, so this is a simple search for one example,
// not an exhaustive search for a counterexample.
bool Node::dominates(Node* sub, Node_List &nlist) {
  assert(this->is_CFG(), "expecting control");
  assert(sub != NULL && sub->is_CFG(), "expecting control");

  // detect dead cycle without regions
  int iterations_without_region_limit = DominatorSearchLimit;

  Node* orig_sub = sub;
  Node* dom      = this;
  bool  met_dom  = false;
  nlist.clear();

  // Walk 'sub' backward up the chain to 'dom', watching for regions.
  // After seeing 'dom', continue up to Root or Start.
  // If we hit a region (backward split point), it may be a loop head.
  // Keep going through one of the region's inputs.  If we reach the
  // same region again, go through a different input.  Eventually we
  // will either exit through the loop head, or give up.
  // (If we get confused, break out and return a conservative 'false'.)
  while (sub != NULL) {
    if (sub->is_top())  break; // Conservative answer for dead code.
    if (sub == dom) {
      if (nlist.size() == 0) {
        // No Region nodes except loops were visited before and the EntryControl
        // path was taken for loops: it did not walk in a cycle.
        return true;
      } else if (met_dom) {
        break;          // already met before: walk in a cycle
      } else {
        // Region nodes were visited. Continue walk up to Start or Root
        // to make sure that it did not walk in a cycle.
        met_dom = true; // first time meet
        iterations_without_region_limit = DominatorSearchLimit; // Reset
     }
    }
    if (sub->is_Start() || sub->is_Root()) {
      // Success if we met 'dom' along a path to Start or Root.
      // We assume there are no alternative paths that avoid 'dom'.
      // (This assumption is up to the caller to ensure!)
      return met_dom;
    }
    Node* up = sub->in(0);
    // Normalize simple pass-through regions and projections:
    up = sub->find_exact_control(up);
    // If sub == up, we found a self-loop.  Try to push past it.
    if (sub == up && sub->is_Loop()) {
      // Take loop entry path on the way up to 'dom'.
      up = sub->in(1); // in(LoopNode::EntryControl);
    } else if (sub == up && sub->is_Region() && sub->req() != 3) {
      // Always take in(1) path on the way up to 'dom' for clone regions
      // (with only one input) or regions which merge > 2 paths
      // (usually used to merge fast/slow paths).
      up = sub->in(1);
    } else if (sub == up && sub->is_Region()) {
      // Try both paths for Regions with 2 input paths (it may be a loop head).
      // It could give conservative 'false' answer without information
      // which region's input is the entry path.
      iterations_without_region_limit = DominatorSearchLimit; // Reset

      bool region_was_visited_before = false;
      // Was this Region node visited before?
      // If so, we have reached it because we accidentally took a
      // loop-back edge from 'sub' back into the body of the loop,
      // and worked our way up again to the loop header 'sub'.
      // So, take the first unexplored path on the way up to 'dom'.
      for (int j = nlist.size() - 1; j >= 0; j--) {
        intptr_t ni = (intptr_t)nlist.at(j);
        Node* visited = (Node*)(ni & ~1);
        bool  visited_twice_already = ((ni & 1) != 0);
        if (visited == sub) {
          if (visited_twice_already) {
            // Visited 2 paths, but still stuck in loop body.  Give up.
            return false;
          }
          // The Region node was visited before only once.
          // (We will repush with the low bit set, below.)
          nlist.remove(j);
          // We will find a new edge and re-insert.
          region_was_visited_before = true;
          break;
        }
      }

      // Find an incoming edge which has not been seen yet; walk through it.
      assert(up == sub, "");
      uint skip = region_was_visited_before ? 1 : 0;
      for (uint i = 1; i < sub->req(); i++) {
        Node* in = sub->in(i);
        if (in != NULL && !in->is_top() && in != sub) {
          if (skip == 0) {
            up = in;
            break;
          }
          --skip;               // skip this nontrivial input
        }
      }

      // Set 0 bit to indicate that both paths were taken.
      nlist.push((Node*)((intptr_t)sub + (region_was_visited_before ? 1 : 0)));
    }

    if (up == sub) {
      break;    // some kind of tight cycle
    }
    if (up == orig_sub && met_dom) {
      // returned back after visiting 'dom'
      break;    // some kind of cycle
    }
    if (--iterations_without_region_limit < 0) {
      break;    // dead cycle
    }
    sub = up;
  }

  // Did not meet Root or Start node in pred. chain.
  // Conservative answer for dead code.
  return false;
}

//------------------------------remove_dead_region-----------------------------
// This control node is dead.  Follow the subgraph below it making everything
// using it dead as well.  This will happen normally via the usual IterGVN
// worklist but this call is more efficient.  Do not update use-def info
// inside the dead region, just at the borders.
static void kill_dead_code( Node *dead, PhaseIterGVN *igvn ) {
  // Con's are a popular node to re-hit in the hash table again.
  if( dead->is_Con() ) return;

  // Can't put ResourceMark here since igvn->_worklist uses the same arena
  // for verify pass with +VerifyOpto and we add/remove elements in it here.
  Node_List  nstack(Thread::current()->resource_area());

  Node *top = igvn->C->top();
  nstack.push(dead);
  bool has_irreducible_loop = igvn->C->has_irreducible_loop();

  while (nstack.size() > 0) {
    dead = nstack.pop();
    if (dead->Opcode() == Op_SafePoint) {
      dead->as_SafePoint()->disconnect_from_root(igvn);
    }
    if (dead->outcnt() > 0) {
      // Keep dead node on stack until all uses are processed.
      nstack.push(dead);
      // For all Users of the Dead...    ;-)
      for (DUIterator_Last kmin, k = dead->last_outs(kmin); k >= kmin; ) {
        Node* use = dead->last_out(k);
        igvn->hash_delete(use);       // Yank from hash table prior to mod
        if (use->in(0) == dead) {     // Found another dead node
          assert (!use->is_Con(), "Control for Con node should be Root node.");
          use->set_req(0, top);       // Cut dead edge to prevent processing
          nstack.push(use);           // the dead node again.
        } else if (!has_irreducible_loop && // Backedge could be alive in irreducible loop
                   use->is_Loop() && !use->is_Root() &&       // Don't kill Root (RootNode extends LoopNode)
                   use->in(LoopNode::EntryControl) == dead) { // Dead loop if its entry is dead
          use->set_req(LoopNode::EntryControl, top);          // Cut dead edge to prevent processing
          use->set_req(0, top);       // Cut self edge
          nstack.push(use);
        } else {                      // Else found a not-dead user
          // Dead if all inputs are top or null
          bool dead_use = !use->is_Root(); // Keep empty graph alive
          for (uint j = 1; j < use->req(); j++) {
            Node* in = use->in(j);
            if (in == dead) {         // Turn all dead inputs into TOP
              use->set_req(j, top);
            } else if (in != NULL && !in->is_top()) {
              dead_use = false;
            }
          }
          if (dead_use) {
            if (use->is_Region()) {
              use->set_req(0, top);   // Cut self edge
            }
            nstack.push(use);
          } else {
            igvn->_worklist.push(use);
          }
        }
        // Refresh the iterator, since any number of kills might have happened.
        k = dead->last_outs(kmin);
      }
    } else { // (dead->outcnt() == 0)
      // Done with outputs.
      igvn->hash_delete(dead);
      igvn->_worklist.remove(dead);
      igvn->set_type(dead, Type::TOP);
      if (dead->is_macro()) {
        igvn->C->remove_macro_node(dead);
      }
      if (dead->is_expensive()) {
        igvn->C->remove_expensive_node(dead);
      }
      CastIINode* cast = dead->isa_CastII();
      if (cast != NULL && cast->has_range_check()) {
        igvn->C->remove_range_check_cast(cast);
      }
      igvn->C->record_dead_node(dead->_idx);
      // Kill all inputs to the dead guy
      for (uint i=0; i < dead->req(); i++) {
        Node *n = dead->in(i);      // Get input to dead guy
        if (n != NULL && !n->is_top()) { // Input is valid?
          dead->set_req(i, top);    // Smash input away
          if (n->outcnt() == 0) {   // Input also goes dead?
            if (!n->is_Con())
              nstack.push(n);       // Clear it out as well
          } else if (n->outcnt() == 1 &&
                     n->has_special_unique_user()) {
            igvn->add_users_to_worklist( n );
          } else if (n->outcnt() <= 2 && n->is_Store()) {
            // Push store's uses on worklist to enable folding optimization for
            // store/store and store/load to the same address.
            // The restriction (outcnt() <= 2) is the same as in set_req_X()
            // and remove_globally_dead_node().
            igvn->add_users_to_worklist( n );
          }
        }
      }
    } // (dead->outcnt() == 0)
  }   // while (nstack.size() > 0) for outputs
  return;
}

//------------------------------remove_dead_region-----------------------------
bool Node::remove_dead_region(PhaseGVN *phase, bool can_reshape) {
  Node *n = in(0);
  if( !n ) return false;
  // Lost control into this guy?  I.e., it became unreachable?
  // Aggressively kill all unreachable code.
  if (can_reshape && n->is_top()) {
    kill_dead_code(this, phase->is_IterGVN());
    return false; // Node is dead.
  }

  if( n->is_Region() && n->as_Region()->is_copy() ) {
    Node *m = n->nonnull_req();
    set_req(0, m);
    return true;
  }
  return false;
}

//------------------------------Ideal_DU_postCCP-------------------------------
// Idealize graph, using DU info.  Must clone result into new-space
Node *Node::Ideal_DU_postCCP( PhaseCCP * ) {
  return NULL;                 // Default to no change
}

//------------------------------hash-------------------------------------------
// Hash function over Nodes.
uint Node::hash() const {
  uint sum = 0;
  for( uint i=0; i<_cnt; i++ )  // Add in all inputs
    sum = (sum<<1)-(uintptr_t)in(i);        // Ignore embedded NULLs
  return (sum>>2) + _cnt + Opcode();
}

//------------------------------cmp--------------------------------------------
// Compare special parts of simple Nodes
uint Node::cmp( const Node &n ) const {
  return 1;                     // Must be same
}

//------------------------------rematerialize-----------------------------------
// Should we clone rather than spill this instruction?
bool Node::rematerialize() const {
  if ( is_Mach() )
    return this->as_Mach()->rematerialize();
  else
    return (_flags & Flag_rematerialize) != 0;
}

//------------------------------needs_anti_dependence_check---------------------
// Nodes which use memory without consuming it, hence need antidependences.
bool Node::needs_anti_dependence_check() const {
  if( req() < 2 || (_flags & Flag_needs_anti_dependence_check) == 0 )
    return false;
  else
    return in(1)->bottom_type()->has_memory();
}


// Get an integer constant from a ConNode (or CastIINode).
// Return a default value if there is no apparent constant here.
const TypeInt* Node::find_int_type() const {
  if (this->is_Type()) {
    return this->as_Type()->type()->isa_int();
  } else if (this->is_Con()) {
    assert(is_Mach(), "should be ConNode(TypeNode) or else a MachNode");
    return this->bottom_type()->isa_int();
  }
  return NULL;
}

// Get a pointer constant from a ConstNode.
// Returns the constant if it is a pointer ConstNode
intptr_t Node::get_ptr() const {
  assert( Opcode() == Op_ConP, "" );
  return ((ConPNode*)this)->type()->is_ptr()->get_con();
}

// Get a narrow oop constant from a ConNNode.
intptr_t Node::get_narrowcon() const {
  assert( Opcode() == Op_ConN, "" );
  return ((ConNNode*)this)->type()->is_narrowoop()->get_con();
}

// Get a long constant from a ConNode.
// Return a default value if there is no apparent constant here.
const TypeLong* Node::find_long_type() const {
  if (this->is_Type()) {
    return this->as_Type()->type()->isa_long();
  } else if (this->is_Con()) {
    assert(is_Mach(), "should be ConNode(TypeNode) or else a MachNode");
    return this->bottom_type()->isa_long();
  }
  return NULL;
}


/**
 * Return a ptr type for nodes which should have it.
 */
const TypePtr* Node::get_ptr_type() const {
  const TypePtr* tp = this->bottom_type()->make_ptr();
  return tp;
}

// Get a double constant from a ConstNode.
// Returns the constant if it is a double ConstNode
jdouble Node::getd() const {
  assert( Opcode() == Op_ConD, "" );
  return ((ConDNode*)this)->type()->is_double_constant()->getd();
}

// Get a float constant from a ConstNode.
// Returns the constant if it is a float ConstNode
jfloat Node::getf() const {
  assert( Opcode() == Op_ConF, "" );
  return ((ConFNode*)this)->type()->is_float_constant()->getf();
}

#ifndef PRODUCT

//----------------------------NotANode----------------------------------------
// Used in debugging code to avoid walking across dead or uninitialized edges.
static inline bool NotANode(const Node* n) {
  if (n == NULL)                   return true;
  if (((intptr_t)n & 1) != 0)      return true;  // uninitialized, etc.
  if (*(address*)n == badAddress)  return true;  // kill by Node::destruct
  return false;
}


//------------------------------find------------------------------------------
// Find a neighbor of this Node with the given _idx
// If idx is negative, find its absolute value, following both _in and _out.
static void find_recur(Compile* C,  Node* &result, Node *n, int idx, bool only_ctrl,
                        VectorSet* old_space, VectorSet* new_space ) {
  int node_idx = (idx >= 0) ? idx : -idx;
  if (NotANode(n))  return;  // Gracefully handle NULL, -1, 0xabababab, etc.
  // Contained in new_space or old_space?   Check old_arena first since it's mostly empty.
  VectorSet *v = C->old_arena()->contains(n) ? old_space : new_space;
  if( v->test(n->_idx) ) return;
  if( (int)n->_idx == node_idx
      debug_only(|| n->debug_idx() == node_idx) ) {
    if (result != NULL)
      tty->print("find: " INTPTR_FORMAT " and " INTPTR_FORMAT " both have idx==%d\n",
                 (uintptr_t)result, (uintptr_t)n, node_idx);
    result = n;
  }
  v->set(n->_idx);
  for( uint i=0; i<n->len(); i++ ) {
    if( only_ctrl && !(n->is_Region()) && (n->Opcode() != Op_Root) && (i != TypeFunc::Control) ) continue;
    find_recur(C, result, n->in(i), idx, only_ctrl, old_space, new_space );
  }
  // Search along forward edges also:
  if (idx < 0 && !only_ctrl) {
    for( uint j=0; j<n->outcnt(); j++ ) {
      find_recur(C, result, n->raw_out(j), idx, only_ctrl, old_space, new_space );
    }
  }
}

// call this from debugger:
Node* find_node(Node* n, int idx) {
  return n->find(idx);
}

//------------------------------find-------------------------------------------
Node* Node::find(int idx) const {
  ResourceArea *area = Thread::current()->resource_area();
  VectorSet old_space(area), new_space(area);
  Node* result = NULL;
  find_recur(Compile::current(), result, (Node*) this, idx, false, &old_space, &new_space );
  return result;
}

//------------------------------find_ctrl--------------------------------------
// Find an ancestor to this node in the control history with given _idx
Node* Node::find_ctrl(int idx) const {
  ResourceArea *area = Thread::current()->resource_area();
  VectorSet old_space(area), new_space(area);
  Node* result = NULL;
  find_recur(Compile::current(), result, (Node*) this, idx, true, &old_space, &new_space );
  return result;
}
#endif



#ifndef PRODUCT

// -----------------------------Name-------------------------------------------
extern const char *NodeClassNames[];
const char *Node::Name() const { return NodeClassNames[Opcode()]; }

static bool is_disconnected(const Node* n) {
  for (uint i = 0; i < n->req(); i++) {
    if (n->in(i) != NULL)  return false;
  }
  return true;
}


//------------------------------dump------------------------------------------
// Dump a Node
void Node::dump(const char* suffix, outputStream *st) const {
  Compile* C = Compile::current();
  bool is_new = C->node_arena()->contains(this);
  C->_in_dump_cnt++;
  st->print("%c%d\t%s\t=== ", is_new ? ' ' : 'o', _idx, Name());

  // Dump the required and precedence inputs
  dump_req(st);
  dump_prec(st);
  // Dump the outputs
  dump_out(st);

  if (is_disconnected(this)) {
    st->cr();
    C->_in_dump_cnt--;
    return;                     // don't process dead nodes
  }

  // Dump node-specific info
  dump_spec(st);

  const Type *t = bottom_type();

  if (t != NULL && (t->isa_instptr() || t->isa_klassptr())) {
    const TypeInstPtr  *toop = t->isa_instptr();
    const TypeKlassPtr *tkls = t->isa_klassptr();
    ciKlass*           klass = toop ? toop->klass() : (tkls ? tkls->klass() : NULL );
    if (klass && klass->is_loaded() && klass->is_interface()) {
      st->print("  Interface:");
    } else if (toop) {
      st->print("  Oop:");
    } else if (tkls) {
      st->print("  Klass:");
    }
    t->dump_on(st);
  } else if (t == Type::MEMORY) {
    st->print("  Memory:");
    MemNode::dump_adr_type(this, adr_type(), st);
  } else if (Verbose || WizardMode) {
    st->print("  Type:");
    if (t) {
      t->dump_on(st);
    } else {
      st->print("no type");
    }
  } else if (t->isa_vect() && this->is_MachSpillCopy()) {
    // Dump MachSpillcopy vector type.
    t->dump_on(st);
  }
  if (is_new) {
    debug_only(dump_orig(debug_orig(), st));
    Node_Notes* nn = C->node_notes_at(_idx);
    if (nn != NULL && !nn->is_clear()) {
      if (nn->jvms() != NULL) {
        st->print(" !jvms:");
        nn->jvms()->dump_spec(st);
      }
    }
  }
  if (suffix) st->print("%s", suffix);
  C->_in_dump_cnt--;
}

//------------------------------dump_req--------------------------------------
void Node::dump_req(outputStream *st) const {
  // Dump the required input edges
  for (uint i = 0; i < req(); i++) {    // For all required inputs
    Node* d = in(i);
    if (d == NULL) {
      st->print("_ ");
    } else if (NotANode(d)) {
      st->print("NotANode ");  // uninitialized, sentinel, garbage, etc.
    } else {
      st->print("%c%d ", Compile::current()->node_arena()->contains(d) ? ' ' : 'o', d->_idx);
    }
  }
}


//------------------------------dump_prec-------------------------------------
void Node::dump_prec(outputStream *st) const {
  // Dump the precedence edges
  int any_prec = 0;
  for (uint i = req(); i < len(); i++) {       // For all precedence inputs
    Node* p = in(i);
    if (p != NULL) {
      if (!any_prec++) st->print(" |");
      if (NotANode(p)) { st->print("NotANode "); continue; }
      st->print("%c%d ", Compile::current()->node_arena()->contains(in(i)) ? ' ' : 'o', in(i)->_idx);
    }
  }
}

//------------------------------dump_out--------------------------------------
void Node::dump_out(outputStream *st) const {
  // Delimit the output edges
  st->print(" [[");
  // Dump the output edges
  for (uint i = 0; i < _outcnt; i++) {    // For all outputs
    Node* u = _out[i];
    if (u == NULL) {
      st->print("_ ");
    } else if (NotANode(u)) {
      st->print("NotANode ");
    } else {
      st->print("%c%d ", Compile::current()->node_arena()->contains(u) ? ' ' : 'o', u->_idx);
    }
  }
  st->print("]] ");
}

//------------------------------dump_nodes-------------------------------------
static void dump_nodes(const Node* start, int d, bool only_ctrl) {
  Node* s = (Node*)start; // remove const
  if (NotANode(s)) return;

  uint depth = (uint)ABS(d);
  int direction = d;
  Compile* C = Compile::current();
  GrowableArray <Node *> nstack(C->live_nodes());

  nstack.append(s);
  int begin = 0;
  int end = 0;
  for(uint i = 0; i < depth; i++) {
    end = nstack.length();
    for(int j = begin; j < end; j++) {
      Node* tp  = nstack.at(j);
      uint limit = direction > 0 ? tp->len() : tp->outcnt();
      for(uint k = 0; k < limit; k++) {
        Node* n = direction > 0 ? tp->in(k) : tp->raw_out(k);

        if (NotANode(n))  continue;
        // do not recurse through top or the root (would reach unrelated stuff)
        if (n->is_Root() || n->is_top())  continue;
        if (only_ctrl && !n->is_CFG()) continue;

        bool on_stack = nstack.contains(n);
        if (!on_stack) {
          nstack.append(n);
        }
      }
    }
    begin = end;
  }
  end = nstack.length();
  if (direction > 0) {
    for(int j = end-1; j >= 0; j--) {
      nstack.at(j)->dump();
    }
  } else {
    for(int j = 0; j < end; j++) {
      nstack.at(j)->dump();
    }
  }
}

//------------------------------dump-------------------------------------------
void Node::dump(int d) const {
  dump_nodes(this, d, false);
}

//------------------------------dump_ctrl--------------------------------------
// Dump a Node's control history to depth
void Node::dump_ctrl(int d) const {
  dump_nodes(this, d, true);
}

// VERIFICATION CODE
// For each input edge to a node (ie - for each Use-Def edge), verify that
// there is a corresponding Def-Use edge.
//------------------------------verify_edges-----------------------------------
void Node::verify_edges(Unique_Node_List &visited) {
  uint i, j, idx;
  int  cnt;
  Node *n;

  // Recursive termination test
  if (visited.member(this))  return;
  visited.push(this);

  // Walk over all input edges, checking for correspondence
  for( i = 0; i < len(); i++ ) {
    n = in(i);
    if (n != NULL && !n->is_top()) {
      // Count instances of (Node *)this
      cnt = 0;
      for (idx = 0; idx < n->_outcnt; idx++ ) {
        if (n->_out[idx] == (Node *)this)  cnt++;
      }
      assert( cnt > 0,"Failed to find Def-Use edge." );
      // Check for duplicate edges
      // walk the input array downcounting the input edges to n
      for( j = 0; j < len(); j++ ) {
        if( in(j) == n ) cnt--;
      }
      assert( cnt == 0,"Mismatched edge count.");
    } else if (n == NULL) {
      assert(i >= req() || i == 0 || is_Region() || is_Phi(), "only regions or phis have null data edges");
    } else {
      assert(n->is_top(), "sanity");
      // Nothing to check.
    }
  }
  // Recursive walk over all input edges
  for( i = 0; i < len(); i++ ) {
    n = in(i);
    if( n != NULL )
      in(i)->verify_edges(visited);
  }
}

//------------------------------verify_recur-----------------------------------
static const Node *unique_top = NULL;

void Node::verify_recur(const Node *n, int verify_depth,
                        VectorSet &old_space, VectorSet &new_space) {
  if ( verify_depth == 0 )  return;
  if (verify_depth > 0)  --verify_depth;

  Compile* C = Compile::current();

  // Contained in new_space or old_space?
  VectorSet *v = C->node_arena()->contains(n) ? &new_space : &old_space;
  // Check for visited in the proper space.  Numberings are not unique
  // across spaces so we need a separate VectorSet for each space.
  if( v->test_set(n->_idx) ) return;

  if (n->is_Con() && n->bottom_type() == Type::TOP) {
    if (C->cached_top_node() == NULL)
      C->set_cached_top_node((Node*)n);
    assert(C->cached_top_node() == n, "TOP node must be unique");
  }

  for( uint i = 0; i < n->len(); i++ ) {
    Node *x = n->in(i);
    if (!x || x->is_top()) continue;

    // Verify my input has a def-use edge to me
    if (true /*VerifyDefUse*/) {
      // Count use-def edges from n to x
      int cnt = 0;
      for( uint j = 0; j < n->len(); j++ )
        if( n->in(j) == x )
          cnt++;
      // Count def-use edges from x to n
      uint max = x->_outcnt;
      for( uint k = 0; k < max; k++ )
        if (x->_out[k] == n)
          cnt--;
      assert( cnt == 0, "mismatched def-use edge counts" );
    }

    verify_recur(x, verify_depth, old_space, new_space);
  }

}

//------------------------------verify-----------------------------------------
// Check Def-Use info for my subgraph
void Node::verify() const {
  Compile* C = Compile::current();
  Node* old_top = C->cached_top_node();
  ResourceMark rm;
  ResourceArea *area = Thread::current()->resource_area();
  VectorSet old_space(area), new_space(area);
  verify_recur(this, -1, old_space, new_space);
  C->set_cached_top_node(old_top);
}
#endif


//------------------------------walk-------------------------------------------
// Graph walk, with both pre-order and post-order functions
void Node::walk(NFunc pre, NFunc post, void *env) {
  VectorSet visited(Thread::current()->resource_area()); // Setup for local walk
  walk_(pre, post, env, visited);
}

void Node::walk_(NFunc pre, NFunc post, void *env, VectorSet &visited) {
  if( visited.test_set(_idx) ) return;
  pre(*this,env);               // Call the pre-order walk function
  for( uint i=0; i<_max; i++ )
    if( in(i) )                 // Input exists and is not walked?
      in(i)->walk_(pre,post,env,visited); // Walk it with pre & post functions
  post(*this,env);              // Call the post-order walk function
}

void Node::nop(Node &, void*) {}

//------------------------------Registers--------------------------------------
// Do we Match on this edge index or not?  Generally false for Control
// and true for everything else.  Weird for calls & returns.
uint Node::match_edge(uint idx) const {
  return idx;                   // True for other than index 0 (control)
}

static RegMask _not_used_at_all;
// Register classes are defined for specific machines
const RegMask &Node::out_RegMask() const {
  ShouldNotCallThis();
  return _not_used_at_all;
}

const RegMask &Node::in_RegMask(uint) const {
  ShouldNotCallThis();
  return _not_used_at_all;
}

//=============================================================================
//-----------------------------------------------------------------------------
void Node_Array::reset( Arena *new_arena ) {
  _a->Afree(_nodes,_max*sizeof(Node*));
  _max   = 0;
  _nodes = NULL;
  _a     = new_arena;
}

//------------------------------clear------------------------------------------
// Clear all entries in _nodes to NULL but keep storage
void Node_Array::clear() {
  Copy::zero_to_bytes( _nodes, _max*sizeof(Node*) );
}

//-----------------------------------------------------------------------------
void Node_Array::grow( uint i ) {
  if( !_max ) {
    _max = 1;
    _nodes = (Node**)_a->Amalloc( _max * sizeof(Node*) );
    _nodes[0] = NULL;
  }
  uint old = _max;
  while( i >= _max ) _max <<= 1;        // Double to fit
  _nodes = (Node**)_a->Arealloc( _nodes, old*sizeof(Node*),_max*sizeof(Node*));
  Copy::zero_to_bytes( &_nodes[old], (_max-old)*sizeof(Node*) );
}

//-----------------------------------------------------------------------------
void Node_Array::insert( uint i, Node *n ) {
  if( _nodes[_max-1] ) grow(_max);      // Get more space if full
  Copy::conjoint_words_to_higher((HeapWord*)&_nodes[i], (HeapWord*)&_nodes[i+1], ((_max-i-1)*sizeof(Node*)));
  _nodes[i] = n;
}

//-----------------------------------------------------------------------------
void Node_Array::remove( uint i ) {
  Copy::conjoint_words_to_lower((HeapWord*)&_nodes[i+1], (HeapWord*)&_nodes[i], ((_max-i-1)*sizeof(Node*)));
  _nodes[_max-1] = NULL;
}

//-----------------------------------------------------------------------------
void Node_Array::sort( C_sort_func_t func) {
  qsort( _nodes, _max, sizeof( Node* ), func );
}

//-----------------------------------------------------------------------------
void Node_Array::dump() const {
#ifndef PRODUCT
  for( uint i = 0; i < _max; i++ ) {
    Node *nn = _nodes[i];
    if( nn != NULL ) {
      tty->print("%5d--> ",i); nn->dump();
    }
  }
#endif
}

//--------------------------is_iteratively_computed------------------------------
// Operation appears to be iteratively computed (such as an induction variable)
// It is possible for this operation to return false for a loop-varying
// value, if it appears (by local graph inspection) to be computed by a simple conditional.
bool Node::is_iteratively_computed() {
  if (ideal_reg()) { // does operation have a result register?
    for (uint i = 1; i < req(); i++) {
      Node* n = in(i);
      if (n != NULL && n->is_Phi()) {
        for (uint j = 1; j < n->req(); j++) {
          if (n->in(j) == this) {
            return true;
          }
        }
      }
    }
  }
  return false;
}

//--------------------------find_similar------------------------------
// Return a node with opcode "opc" and same inputs as "this" if one can
// be found; Otherwise return NULL;
Node* Node::find_similar(int opc) {
  if (req() >= 2) {
    Node* def = in(1);
    if (def && def->outcnt() >= 2) {
      for (DUIterator_Fast dmax, i = def->fast_outs(dmax); i < dmax; i++) {
        Node* use = def->fast_out(i);
        if (use->Opcode() == opc &&
            use->req() == req()) {
          uint j;
          for (j = 0; j < use->req(); j++) {
            if (use->in(j) != in(j)) {
              break;
            }
          }
          if (j == use->req()) {
            return use;
          }
        }
      }
    }
  }
  return NULL;
}


//--------------------------unique_ctrl_out------------------------------
// Return the unique control out if only one. Null if none or more than one.
Node* Node::unique_ctrl_out() {
  Node* found = NULL;
  for (uint i = 0; i < outcnt(); i++) {
    Node* use = raw_out(i);
    if (use->is_CFG() && use != this) {
      if (found != NULL) return NULL;
      found = use;
    }
  }
  return found;
}

//=============================================================================
//------------------------------yank-------------------------------------------
// Find and remove
void Node_List::yank( Node *n ) {
  uint i;
  for( i = 0; i < _cnt; i++ )
    if( _nodes[i] == n )
      break;

  if( i < _cnt )
    _nodes[i] = _nodes[--_cnt];
}

//------------------------------dump-------------------------------------------
void Node_List::dump() const {
#ifndef PRODUCT
  for( uint i = 0; i < _cnt; i++ )
    if( _nodes[i] ) {
      tty->print("%5d--> ",i);
      _nodes[i]->dump();
    }
#endif
}

void Node_List::dump_simple() const {
#ifndef PRODUCT
  for( uint i = 0; i < _cnt; i++ )
    if( _nodes[i] ) {
      tty->print(" %d", _nodes[i]->_idx);
    } else {
      tty->print(" NULL");
    }
#endif
}

//=============================================================================
//------------------------------remove-----------------------------------------
void Unique_Node_List::remove( Node *n ) {
  if( _in_worklist[n->_idx] ) {
    for( uint i = 0; i < size(); i++ )
      if( _nodes[i] == n ) {
        map(i,Node_List::pop());
        _in_worklist >>= n->_idx;
        return;
      }
    ShouldNotReachHere();
  }
}

//-----------------------remove_useless_nodes----------------------------------
// Remove useless nodes from worklist
void Unique_Node_List::remove_useless_nodes(VectorSet &useful) {

  for( uint i = 0; i < size(); ++i ) {
    Node *n = at(i);
    assert( n != NULL, "Did not expect null entries in worklist");
    if( ! useful.test(n->_idx) ) {
      _in_worklist >>= n->_idx;
      map(i,Node_List::pop());
      // Node *replacement = Node_List::pop();
      // if( i != size() ) { // Check if removing last entry
      //   _nodes[i] = replacement;
      // }
      --i;  // Visit popped node
      // If it was last entry, loop terminates since size() was also reduced
    }
  }
}

//=============================================================================
void Node_Stack::grow() {
  size_t old_top = pointer_delta(_inode_top,_inodes,sizeof(INode)); // save _top
  size_t old_max = pointer_delta(_inode_max,_inodes,sizeof(INode));
  size_t max = old_max << 1;             // max * 2
  _inodes = REALLOC_ARENA_ARRAY(_a, INode, _inodes, old_max, max);
  _inode_max = _inodes + max;
  _inode_top = _inodes + old_top;        // restore _top
}

// Node_Stack is used to map nodes.
Node* Node_Stack::find(uint idx) const {
  uint sz = size();
  for (uint i=0; i < sz; i++) {
    if (idx == index_at(i) )
      return node_at(i);
  }
  return NULL;
}

//=============================================================================
uint TypeNode::size_of() const { return sizeof(*this); }
#ifndef PRODUCT
void TypeNode::dump_spec(outputStream *st) const {
  if( !Verbose && !WizardMode ) {
    // standard dump does this in Verbose and WizardMode
    st->print(" #"); _type->dump_on(st);
  }
}
#endif
uint TypeNode::hash() const {
  return Node::hash() + _type->hash();
}
uint TypeNode::cmp( const Node &n ) const
{ return !Type::cmp( _type, ((TypeNode&)n)._type ); }
const Type *TypeNode::bottom_type() const { return _type; }
const Type *TypeNode::Value( PhaseTransform * ) const { return _type; }

//------------------------------ideal_reg--------------------------------------
uint TypeNode::ideal_reg() const {
  return _type->ideal_reg();
}
