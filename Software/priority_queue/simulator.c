// -----------------------------------
// CSCI 340 - Operating Systems
// Fall 2018
// simulator.c file
//
// Homework 5 - OS Simulator
//
// -----------------------------------

#include "simulator.h"

// ------------------------------------
// Function that initializes the simulator
//
// Arguments:	none
//
// Return:     	none
//
void initialize()
{

	int i;
	job_t *job;

	srand(RAND_SEED);

	pq_size = EMPTY;

	// ----------------------------------------------
	// Add INITIAL_JOBS to the linked list
	//
	// Please do not modify

	job = malloc(sizeof(job_t));
	job->s_time = MIN_SERVICE_TIME + rand() % (MAX_SERVICE_TIME + 1);
	job->e_time = ZERO;
	job->srt = (job->s_time - job->e_time);

	pq_head = job;

	for (i = 0; i < INITIAL_JOBS - 1; i++)
	{

		job->next = malloc(sizeof(job_t));
		job = job->next;
		job->s_time = MIN_SERVICE_TIME + rand() % (MAX_SERVICE_TIME + 1);
		job->e_time = ZERO;
		job->srt = (job->s_time - job->e_time);

		pq_size++;
	}

	pq_tail = job;
	pq_tail->next = NULL;

	// ----------------------------------------------
	// TODO: Initialize your locking variables here

} // end initialize function

// ------------------------------------
// Function that simulates an OS scheduler.
// The scheduler is a priority queue,
// specifically a min-heap that uses the
// srt metric
//
//
// Arguments:	none
//
// Return:     	noe
//
void scheduler()
{

	// ----------------------------
	// TODO: Basic scheduling algorithm
	// ----------------------------
	// Very simple, run min-heapify on
	// linked list to create priority
	// queue


	//printf("pq_size: %d\n", pq_size);
	int i;
	for(i = floor(pq_size/2); i >= 0; i--){
		//printf("i = %d\n", i);
		int k = i;
		int v = get_list_element_(k)->srt;
		int heap = 0;
		
		while(heap != 1 && (2 * k + 1) <= pq_size){
			int j = 2 * k + 1;

			if(j < pq_size){ //if there are two children

				if(get_list_element_(j)->srt >= get_list_element_(j + 1)->srt){

					j = j + 1;
				}
			}
			if(v <= get_list_element_(j)->srt){
				heap = 1;
			}
			else{
				//printf("in else\n");

				job_t * temp = malloc(sizeof(job_t *));

				job_t * k_point = get_list_element_(k);
				job_t * j_point = get_list_element_(j);

				temp->e_time = k_point->e_time;
				temp->s_time = k_point->s_time;
				temp->srt = k_point->srt;

				k_point->e_time = j_point->e_time;
				k_point->s_time = j_point->s_time;
				k_point->srt = j_point->srt;	

				j_point->e_time = temp->e_time;
				j_point->s_time = temp->s_time;
				j_point->srt = temp->srt;	

				printf("j is %d, k is %d, switched %d and %d\n\n", j,k,j_point->srt, k_point->srt);
				k = j;
				
			}
			//printf("loop\n");
		}
		//printf("out of while\n");
		//* get_list_element_(k) = * v;
		get_list_element_(k)->srt = v;
	}
	printf("out of for\n");
	pq_head = get_list_element_(0);
	pq_tail = get_list_element_(pq_size);

} // end scheduler function

// ------------------------------------
// Function that simulates an OS
// dispatcher. In particular, the dispatcher
// removes the job at the front of the priority
// queue and then hands it off to the CPU
//
//
// Arguments:	none
//
// Return:     	none
//
void dispatcher()
{

	// -------------------------
	// concurrency constraints
	// -------------------------
	// cannot remove a job from priority queue:
	// 	- if it is empty
	// 	- if cpu or forker is adding a job
	scheduler();
	print_pq();
	while (TRUE)
	{

		// --------------------------------
		// TODO: Basic algorithm
		// --------------------------------
		// 1. run scheduler to identify next priority queue job
		// 2. remove job from priority queue (update pq_size)
		// 3. hand job off to cpu (set cpu_job equal to job)
		// 4. goto (1) and repeat
	}

} // end dispatcher function

// ------------------------------------
// Function that simulates a CPU.
// The sole job of the CPU is to execute at
// one time quantum (q) intervals. At each
// q the job_t execution time (e_time field)
// is increment by one.
//
//
// Arguments:	none
//
// Return:     	none
//
void cpu()
{

	// -------------------------
	// concurrency constraints
	// -------------------------
	// cannot add job to priority queue:
	// 	- if it is full
	// 	- if dispatcher is running scheduler to remove next job
	// 	- if forker is adding a job

	while (TRUE)
	{

		// --------------------------------
		// TODO: Basic algorithm
		// --------------------------------
		// 1. run cpu job for only one time quantum
		//    and then update e_time and srt fields
		// 2.a. if cpu_job is not finished (srt>0)
		//      add to priority queue (update pq_size)
		// 2.b. if cpu_job is finished (srt==0)
		//      free job_t malloc'd memory
		// 3. goto (1) and repeat
	}

} // end cpu function

// ------------------------------------
// Function that simulates the OS
// creating a new job
//
// Arguments:	none
//
// Return:     	none
//
void forker()
{

	// -------------------------
	// concurrency constraints
	// -------------------------
	// cannot add job to priority queue:
	// 	- if it is full
	// 	- if dispatcher is running scheduler to remove next job
	// 	- if cpu is adding a job

	while (TRUE)
	{

		// --------------------------------
		// TODO: Basic algorithm
		// --------------------------------
		// 1. Create a new job
		//    - s_time is a random number in [MIN_EXE_TIME MAX_EXE_TIME]
		//    - s_time is intialized to zero
		//	  - see initialize function for an example
		// 2. add job to linked list (update list_size)
		// 3. sleep for nanoseconds (you determine)
		// 4. goto (1) and repeat
	}

} // end forker

// ------------------------------------
// Function that walks the linked
// list and returns the job_t element at
// the specified index position in the linked
// list.
//
//
// Arguments:	linked list index position
//
// Return:     	job_t pointer	-> Valid index position
//				NULL 			->	Not a valid index position
//
job_t *get_list_element_(int index_position)
{
	// --------------------------------
	// TODO: Add code here
	// --------------------------------
	if (index_position < 0 || index_position > pq_size)
	{
		return NULL;
	}
	//starts at priority queue head
	job_t *current_index = malloc(sizeof(job_t *));
	current_index = pq_head;

	int i;
	//increments pointer to index_position
	for (i = 0; i < index_position; i++)
	{
		current_index = current_index->next;
	}

	return current_index;

} // end get_list_element function

// ------------------------------------
// Function prototype to sleep a defined
// number of nanoseconds
//
// Arguments:	number of nanosecs to sleep
//
// Return:     	none
//
void nsleep(long nanosec)
{

	// Please do not modify this code

	struct timespec t_spec;

	t_spec.tv_sec = 0;
	t_spec.tv_nsec = nanosec;

	nanosleep(&t_spec, NULL);

} // end nsleep function

// ------------------------------------
// Function that prints all the
// job_t elements in the priority queue
//
// Used for debugging purposes only
//
// May modify as you see fit
//
// Arguments:	none
//
// Return:     	none
//
void print_pq()
{

	int count = 0;

	job_t *job = pq_head;

	if (DEBUG)
		printf("Total number of jobs in priority queue = %d\n", pq_size + 1);

	while (job != NULL)
	{

		if (DEBUG)
		{
			printf("--------------------------\n");
			printf("job_t[%d]->e_time = %d\n", ++count, job->e_time);
			printf("job_t[%d]->s_time = %d\n", count, job->s_time);
			printf("job_t[%d]->srt = %d\n", count, job->srt);
		}

		job = job->next;
	}

} // end print_pq function