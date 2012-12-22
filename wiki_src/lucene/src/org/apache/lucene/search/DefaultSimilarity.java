package org.apache.lucene.search;

import org.apache.lucene.index.FieldInvertState;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/** Expert: Default scoring implementation. */
public class DefaultSimilarity extends Similarity {

  /** Implemented as
   *  <code>state.getBoost()*lengthNorm(numTerms)</code>, where
   *  <code>numTerms</code> is {@link FieldInvertState#getLength()} if {@link
   *  #setDiscountOverlaps} is false, else it's {@link
   *  FieldInvertState#getLength()} - {@link
   *  FieldInvertState#getNumOverlap()}.
   *
   *  @lucene.experimental */
  @Override
  public float computeNorm(String field, FieldInvertState state) {
    final int numTerms;
    if (discountOverlaps)
      numTerms = state.getLength() - state.getNumOverlap();
    else
      numTerms = state.getLength();

 //modified by raghavan 3mar start


	   if(field.equals("title"))
	   {
		   //System.out.println("field==title");
		   return 2 * (float) (1 * Math.log(numTerms));
	   }
    else
    {
		//System.out.println("field!=title, field="+field);
		//return super.lengthNorm(field, numTerms);
		return state.getBoost() * ((float) (1.0 / Math.sqrt(numTerms)));
	}

//return 1;
//end
    //return state.getBoost() * ((float) (1.0 / Math.sqrt(numTerms)));
  }

  /** Implemented as <code>1/sqrt(sumOfSquaredWeights)</code>. */
  @Override
  public float queryNorm(float sumOfSquaredWeights) {
//System.out.println("in queryNorm");
    return (float)(1.0 / Math.sqrt(sumOfSquaredWeights));
  }

  /** Implemented as <code>sqrt(freq)</code>. */
  @Override
  public float tf(float freq) {
	  //System.out.println("in tf method");
	  //modified by raghavan 3mar
	 return (float)(freq*0.5);

    //return (float)Math.sqrt(freq);
	   
	  
	  
  }

  /** Implemented as <code>1 / (distance + 1)</code>. */
  @Override
  public float sloppyFreq(int distance) {
    return 1.0f / (distance + 1);
  }

  /** Implemented as <code>log(numDocs/(docFreq+1)) + 1</code>. */
  @Override
  public float idf(int docFreq, int numDocs) {
	  //modified by raghavan 3mar
	  //System.out.println("in idf method");
    //return (float)(Math.log(numDocs/(double)(docFreq+1)) + 1.0);
    //return (float)(Math.log(numDocs/(double)(docFreq)) + 1.0);
	  float var;
	  if(docFreq!=0)
	 var = (float) (numDocs/(docFreq));
	  else
		  var = 0;

   return (float) Math.pow( var , 0.6);

    //return 1;
  }

  /** Implemented as <code>overlap / maxOverlap</code>. */
  @Override
  public float coord(int overlap, int maxOverlap) {
    return (2*overlap / (float)maxOverlap);
  }

  // Default true
  protected boolean discountOverlaps = true;

  /** Determines whether overlap tokens (Tokens with
   *  0 position increment) are ignored when computing
   *  norm.  By default this is true, meaning overlap
   *  tokens do not count when computing norms.
   *
   *  @lucene.experimental
   *
   *  @see #computeNorm
   */
  public void setDiscountOverlaps(boolean v) {

    discountOverlaps = v;
  }

  /** @see #setDiscountOverlaps */
  public boolean getDiscountOverlaps() {
    return discountOverlaps;
  }
}
