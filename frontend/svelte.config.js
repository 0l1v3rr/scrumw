import adapter from '@sveltejs/adapter-auto';
import preprocess from 'svelte-preprocess';

/** @type {import('@sveltejs/kit').Config} */
const config = {
	// Consult https://github.com/sveltejs/svelte-preprocess
	// for more information about preprocessors
	preprocess: preprocess(),

	kit: {
		adapter: adapter()
	},
	
	vite: {
		server: {
			fs: {
				allow: ["."]
			}
		},
		hmr: {
			protocol: 'ws',
			host: '0.0.0.0',
			port: '24678'
		}
	}

};

export default config;
