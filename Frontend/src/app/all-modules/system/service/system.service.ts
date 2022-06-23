import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { retry } from 'rxjs/operators';


export interface Message {
	id: number;
	text: string;
}



@Injectable({
  providedIn: 'root'
})
export class SystemService {


  private callbacks: Function[];
	private localStorageKey: string;



  constructor(private http:HttpClient) {
    this.localStorageKey = "load-data-in-background-angular11";
		this.callbacks = [];

		this.watchStorage();
  }

  public sendGetRequest(apiURL, queryParams){
    console.log('@sendGetRequest');
    return this.http.get<any>(apiURL, {params: queryParams}).pipe( retry(3));
  }

  public sendPostRequest(apiURL, formData){
    console.log('@sendPostRequest');
    return this.http.post(apiURL, formData);
  }

  public sendPutRequest(apiURL, formData){
    console.log('@sendPutRequest');
    return this.http.put(apiURL, formData);
  }

  public sendDeleteRequest(apiURL, formData){
    console.log('@sendDeleteRequest');
    return this.http.delete(apiURL, formData);

  }



  //for subscribe
  public async addMessage( text: string ) : Promise<number> {

		await this.simulateNetwork();

		var id = Date.now();
		var messages = this.loadFromDisk();

		messages.push({
			id: id,
			text: text
		});

		this.saveToDisk( messages );

		return( id );

	}


	// I delete the message with the given ID.
	public async deleteMessage( id: number ) : Promise<void> {

		await this.simulateNetwork();

		var messages = this.loadFromDisk().filter(
			( message ) => {

				return( message.id !== id );

			}
		);

		this.saveToDisk( messages );

	}


	// I get all of the current messages.
	public async getMessages() : Promise<Message[]> {

		await this.simulateNetwork();

		return( this.loadFromDisk() );

	}


	// I subscribe the given callback to changes in the messages collection.
	// --
	// CAUTION: The callback is only triggered when changes are made by other browser
	// tabs. It uses the "storage" event to see when the underlying data changed.
	public subscribe( callback: Function ) : void {

		this.callbacks.push( callback );

	}

	// ---
	// PRIVATE METHODS.
	// ---

	// I load the messages collection out of LocalStorage.
	private loadFromDisk() : Message[] {

		var data = localStorage.getItem( this.localStorageKey );

		if ( ! data ) {

			return( [] );

		}

		// CAUTION: For the sake of simplicity, I'm just blindly trusting that I can
		// parse the storage value into a valid messages collection. In a production
		// environment, I MIGHT want to do some additional validation (maybe).
		return( JSON.parse( data ) as Message[] );

	}


	// I save the messages collection to LocalStorage.
	private saveToDisk( messages: Message[] ) : void {

		localStorage.setItem( this.localStorageKey, JSON.stringify( messages ) );

	}


	// I provide a random delay to simulate some network latency while we work with a
	// purely local data model.
	private simulateNetwork() : Promise<void> {

		var promise = new Promise<void>(
			( resolve ) => {

				setTimeout( resolve, ( 100 + ( Math.random() * 1000 ) ) );

			}
		);

		return( promise );

	}


	// I listen for storage events and alert subscribers to changes triggered by other
	// browser tabs.
	private watchStorage() : void {

		window.addEventListener(
			"storage",
			( event: StorageEvent ) => {

				if ( event.key === this.localStorageKey ) {

					// CAUTION: In a production environment, I'd wrap the individual
					// callback invocations in a TRY/CATCH so that I could catch errors
					// without disrupting the entire set of callbacks. But, for the sake
					// of simplicity, I'm just firing them with abandon!
					for ( var callback of this.callbacks ) {

						callback();

					}

				}

			}
		);

	}

}
